package ru.natal1a_chuklina.simple_db_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.natal1a_chuklina.simple_db_service.dto.*;
import ru.natal1a_chuklina.simple_db_service.dto.result.*;
import ru.natal1a_chuklina.simple_db_service.exception.ValidationException;
import ru.natal1a_chuklina.simple_db_service.utils.Constants;
import ru.natal1a_chuklina.simple_db_service.utils.JsonFileInteractor;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DBInteractionService {
    private final JsonFileInteractor fileInteractor;
    private final DtoMapper dtoMapper;
    private final CommandInput commandInputDto;
    private DBStorage dbStorage;

    public void execute() {
        DBConfiguration dbConfigurationDto;

        try {
            dbConfigurationDto = readDBConfigurationFile();
            validateConfiguration(dbConfigurationDto);
        } catch (IOException e) {
            log.error("Failed to read configuration file. MSG: {}", e.getMessage());
            return;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return;
        }

        try (Connection connection = createConnection(dbConfigurationDto)) {
            this.dbStorage = new DBStorage(connection);
            log.info("Connected to DB.");
            Result result;

            switch (commandInputDto.getOperation()) {
                case STATISTICS: {
                    StatInput inputDto = readInputFile(StatInput.class);
                    result = getStat(inputDto);
                    break;
                }
                case SEARCH: {
                    SearchInput inputDto = readInputFile(SearchInput.class);
                    result = search(inputDto);
                    break;
                }
                default: {
                    String errorMessage = String.format("Illegal operation %s", commandInputDto.getOperation());
                    log.error(errorMessage);
                    result = new ErrorResult(errorMessage);
                }
            }

            writeOutputFile(result);
        } catch (SQLException e) {
            log.error("Failed to connect to DB. MSG: {}", e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private Result search(SearchInput searchInputDto) {
        if (searchInputDto.getCriteria() == null) {
            String errorMessage = "Input file must contains search criteria";
            log.error(errorMessage);
            return new ErrorResult(errorMessage);
        }

        List<Result> results = new ArrayList<>();

        for (Map<String, String> values : searchInputDto.getCriteria()) {
            results.add(getCriterionResult(values));
        }

        return new SearchOutput(results);
    }

    private Result getCriterionResult(Map<String, String> values) {
        try {
            List<Customer> customers;
            Criteria criterion = Criteria.findByKeys(values.keySet());

            switch (criterion) {
                case LAST_NAME: {
                    String fieldName = criterion.getRequiredKeys().get(0);
                    String lastName = values.get(fieldName);
                    validateString(lastName, fieldName);
                    customers = dbStorage.getCustomersByLastName(lastName);
                    break;
                }
                case PRODUCT_NAME_AND_MIN_TIMES: {
                    String productFieldName = criterion.getRequiredKeys().get(0);
                    String minTimesFieldName = criterion.getRequiredKeys().get(1);
                    String productName = values.get(productFieldName);
                    validateString(productName, productFieldName);
                    validateString(values.get(minTimesFieldName), minTimesFieldName);
                    int minTimes = Integer.parseInt(values.get(criterion.getRequiredKeys().get(1)));
                    customers = dbStorage.getCustomersByProductNameAndMinTimes(productName, minTimes);
                    break;
                }
                case MIN_MAX_EXPENSES: {
                    String minExpensesFieldName = criterion.getRequiredKeys().get(0);
                    String maxExpensesFieldName = criterion.getRequiredKeys().get(1);
                    validateString(values.get(minExpensesFieldName), minExpensesFieldName);
                    validateString(values.get(maxExpensesFieldName), maxExpensesFieldName);
                    double minExpenses = Double.parseDouble(values.get(minExpensesFieldName));
                    double maxExpenses = Double.parseDouble(values.get(maxExpensesFieldName));
                    customers = dbStorage.getCustomersByMinAndMaxExpenses(minExpenses, maxExpenses);
                    break;
                }
                case PASSIVE_CUSTOMERS: {
                    String fieldName = criterion.getRequiredKeys().get(0);
                    validateString(values.get(fieldName), fieldName);
                    int limit = Integer.parseInt(values.get(fieldName));
                    customers = dbStorage.getPassiveCustomers(limit);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Criterion not found.");
            }

            return new CriterionResult(values, customers);
        } catch (RuntimeException e) {
            String errorMessage = String.format("Failed to read criterion. MSG: %s", e.getMessage());
            log.error(errorMessage);
            return new ErrorResult(errorMessage);
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to get data from DB. MSG: %s", e.getMessage());
            log.error(errorMessage);
            return new ErrorResult(errorMessage);
        }
    }


    private Result getStat(StatInput statInputDto) {
        if (statInputDto.getEndDate() == null || statInputDto.getStartDate() == null) {
            String errorMessage = "Incorrect input file. Start and end dates mustn't be null";
            log.error(errorMessage);
            return new ErrorResult(errorMessage);
        }

        if (statInputDto.getStartDate().isAfter(statInputDto.getEndDate())) {
            String errorMessage = "Incorrect dates: start date must be earlier than or equal to end date.";
            log.error(errorMessage);
            return new ErrorResult(errorMessage);
        }

        try {
            return dbStorage.getStatistic(statInputDto);
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to get data from DB. MSG: %s", e.getMessage());
            log.error(errorMessage);
            return new ErrorResult(errorMessage);
        }
    }

    private Connection createConnection(DBConfiguration dbConfigurationDto) throws SQLException {
        return DriverManager.getConnection(dbConfigurationDto.getUrl(), dbConfigurationDto.getUsername(),
                dbConfigurationDto.getPassword());
    }

    private DBConfiguration readDBConfigurationFile() throws IOException {
        String configuration = fileInteractor.read(Paths.get(Constants.CONFIGURATION_FILE_NAME));
        return dtoMapper.stringToDto(configuration, DBConfiguration.class);
    }

    private <T> T readInputFile(Class<T> clazz) throws IOException {
        String input = fileInteractor.read(Paths.get(commandInputDto.getInputFile()));
        return dtoMapper.stringToDto(input, clazz);
    }

    private <T extends Result> void writeOutputFile(T outputDto) throws IOException {
        String output = dtoMapper.dtoToString(outputDto);
        fileInteractor.write(Paths.get(commandInputDto.getOutputFile()), output);
    }

    private void validateConfiguration(DBConfiguration dbConfiguration) {
        validateString(dbConfiguration.getUrl(), "db url");
        validateString(dbConfiguration.getUsername(), "db username");
        validateString(dbConfiguration.getPassword(), "db password");
    }

    private void validateString(String s, String fieldName) {
        if (s == null || StringUtils.isBlank(s)) {
            throw new ValidationException(String.format("Field %s mustn't be null or empty.", fieldName));
        }
    }
}
