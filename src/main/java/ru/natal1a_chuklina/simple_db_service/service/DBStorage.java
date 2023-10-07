package ru.natal1a_chuklina.simple_db_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.natal1a_chuklina.simple_db_service.dto.StatInput;
import ru.natal1a_chuklina.simple_db_service.dto.result.Customer;
import ru.natal1a_chuklina.simple_db_service.dto.result.CustomerWithPurchases;
import ru.natal1a_chuklina.simple_db_service.dto.result.Purchase;
import ru.natal1a_chuklina.simple_db_service.dto.result.StatOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Slf4j
public class DBStorage {
    private final Connection connection;

    public List<Customer> getCustomersByLastName(String lastName) throws SQLException {
        String sqlQuery =
                "SELECT  c.first_name, " +
                        "c.last_name " +
                        "FROM customers AS c " +
                        "WHERE c.last_name = ?;";

        try (PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setString(1, lastName);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Customer> customers = mapResultSetToCustomerDto(resultSet);
            log.info("List of customers with last name {} received. List length: {}.", lastName, customers.size());
            return customers;
        }
    }

    public List<Customer> getCustomersByProductNameAndMinTimes(String productName, int minTimes) throws SQLException {
        String sqlQuery =
                "SELECT  c.first_name, " +
                        "c.last_name " +
                        "FROM customers AS c " +
                        "LEFT JOIN purchases AS p ON c.id = p.customer_id " +
                        "LEFT JOIN products AS pr ON pr.id = p.product_id " +
                        "WHERE pr.name = ? " +
                        "GROUP BY c.id " +
                        "HAVING COUNT(*) >= ?;";

        try (PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setString(1, productName);
            prepareStatement.setInt(2, minTimes);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Customer> customers = mapResultSetToCustomerDto(resultSet);
            log.info("List of customers who bought product with name {} at least {} times received. List length: {}.",
                    productName, minTimes, customers.size());
            return customers;
        }
    }

    public List<Customer> getCustomersByMinAndMaxExpenses(double minExpenses, double maxExpenses) throws SQLException {
        String sqlQuery =
                "SELECT  c.first_name, " +
                        "c.last_name " +
                        "FROM customers AS c " +
                        "LEFT JOIN purchases AS p ON c.id = p.customer_id " +
                        "LEFT JOIN products AS pr ON pr.id = p.product_id " +
                        "GROUP BY c.id " +
                        "HAVING SUM(pr.price) BETWEEN ? AND ?;";

        try (PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setDouble(1, minExpenses);
            prepareStatement.setDouble(2, maxExpenses);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Customer> customers = mapResultSetToCustomerDto(resultSet);
            log.info("List of customers whose expenses are between {} and {} received. List length: {}.", minExpenses,
                    maxExpenses, customers.size());
            return customers;
        }
    }

    public List<Customer> getPassiveCustomers(int limit) throws SQLException {
        String sqlQuery =
                "SELECT  c.first_name, " +
                        "c.last_name " +
                        "FROM customers AS c " +
                        "LEFT JOIN purchases AS p ON c.id = p.customer_id " +
                        "LEFT JOIN products AS pr ON pr.id = p.product_id " +
                        "GROUP BY c.id " +
                        "ORDER BY SUM(pr.price) NULLS FIRST " +
                        "LIMIT ?;";

        try (PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery)) {
            prepareStatement.setInt(1, limit);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Customer> customers = mapResultSetToCustomerDto(resultSet);
            log.info("List of the {} most passive customers received. List length: {}.", limit, customers.size());
            return customers;
        }
    }

    public StatOutput getStatistic(StatInput statInput) throws SQLException {
        String sqlQuery = String.format(
                "CREATE OR REPLACE VIEW days_view AS " +
                        "SELECT days " +
                        "FROM generate_series(timestamp '%s',  timestamp '%s', interval '1 day') AS days " +
                        "WHERE EXTRACT ('ISODOW' " +
                        "               FROM days) < 6; " +
                        " " +
                        "CREATE OR REPLACE VIEW customers_view AS " +
                        "SELECT concat(c.first_name, ' ', c.last_name) AS full_name, " +
                        "       array_agg(pr.id) AS purchases_id, " +
                        "       sum(pr.price) AS total " +
                        "FROM purchases p " +
                        "LEFT JOIN customers c ON c.id = p.customer_id " +
                        "LEFT JOIN products pr ON pr.id = p.product_id " +
                        "WHERE p.purchase_date in " +
                        "    (SELECT days " +
                        "     FROM days_view) " +
                        "GROUP BY c.id; " +
                        " " +
                        "SELECT " +
                        "  (SELECT count(d.days) " +
                        "   FROM days_view AS d) AS total_days, " +
                        "       count(c.total) AS customers_count, " +
                        "       sum(c.total) AS total_expenses, " +
                        "       avg(c.total) AS avg_expenses " +
                        "FROM customers_view AS c; " +
                        " " +
                        "SELECT full_name, " +
                        "       total AS customer_expenses, " +
                        "  (SELECT array_agg(concat(ip.name, ':', ip.cost)) " +
                        "   FROM " +
                        "     (SELECT p.name, " +
                        "             sum(p.price) AS cost " +
                        "      FROM unnest(purchases_id) " +
                        "      LEFT JOIN products AS p ON p.id = UNNEST " +
                        "      GROUP BY p.id " +
                        "      ORDER BY cost DESC) AS ip) AS products " +
                        "FROM customers_view AS cv " +
                        "ORDER BY total DESC;", statInput.getStartDate(), statInput.getEndDate());

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
            StatOutput statOutput = new StatOutput();
            statement.getMoreResults();
            statement.getMoreResults();

            boolean containsCustomers;
            try (ResultSet resultSet = statement.getResultSet()) {
                containsCustomers = fillCommonStatFields(resultSet, statOutput);
            }

            List<CustomerWithPurchases> customers = new ArrayList<>();
            if (containsCustomers) {
                statement.getMoreResults();
                try (ResultSet resultSet = statement.getResultSet()) {
                    fillCustomersWithPurchases(resultSet, customers);
                }
            }

            statOutput.setCustomers(customers);
            log.info("Statistics received for {} days", statOutput.getTotalDays());
            return statOutput;
        }
    }

    private boolean fillCommonStatFields(ResultSet rs, StatOutput statOutput) throws SQLException {
        rs.next();
        int totalDays = rs.getInt("total_days");
        statOutput.setTotalDays(totalDays);

        int customersCount = rs.getInt("customers_count");
        if (customersCount == 0) {
            log.info("Customers for requested period weren't found");
            return false;
        }

        double totalExpenses = rs.getDouble("total_expenses");
        statOutput.setTotalExpenses(totalExpenses);
        double avgExpenses = rs.getDouble("avg_expenses");
        statOutput.setAvgExpenses(avgExpenses);
        return true;
    }

    private void fillCustomersWithPurchases(ResultSet rs, List<CustomerWithPurchases> customers) throws SQLException {
        while (rs.next()) {
            String name = rs.getString("full_name");
            double totalExpenses = rs.getDouble("customer_expenses");
            List<Purchase> purchases = new ArrayList<>();
            try (ResultSet resultSet = rs.getArray("products").getResultSet()) {
                fillPurchases(resultSet, purchases);
            }

            customers.add(new CustomerWithPurchases(name, purchases, totalExpenses));
        }
    }

    private void fillPurchases(ResultSet rs, List<Purchase> purchases) throws SQLException {
        while (rs.next()) {
            String purchaseData = rs.getString("value");
            StringTokenizer tokenizer = new StringTokenizer(purchaseData, ":");
            String name = tokenizer.nextToken();
            double expenses = Double.parseDouble(tokenizer.nextToken());
            purchases.add(new Purchase(name, expenses));
        }
    }

    private List<Customer> mapResultSetToCustomerDto(ResultSet rs) throws SQLException {
        List<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            customers.add(new Customer(lastName, firstName));
        }

        return customers;
    }
}
