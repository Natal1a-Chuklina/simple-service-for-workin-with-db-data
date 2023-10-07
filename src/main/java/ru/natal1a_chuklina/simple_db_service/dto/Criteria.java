package ru.natal1a_chuklina.simple_db_service.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Criteria {
    LAST_NAME("lastName"),
    PRODUCT_NAME_AND_MIN_TIMES("productName", "minTimes"),
    MIN_MAX_EXPENSES("minExpenses", "maxExpenses"),
    PASSIVE_CUSTOMERS("badCustomers");

    private final List<String> requiredKeys;

    Criteria(String... values) {
        this.requiredKeys = Arrays.stream(values).collect(Collectors.toList());
    }

    public static Criteria findByKeys(Set<String> keys) {
        for (Criteria c : Criteria.values()) {
            Set<String> keySet = new HashSet<>(c.requiredKeys);

            if (keySet.equals(keys)) {
                return c;
            }
        }

        throw new IllegalArgumentException(String.format("Criterion not found. Keys: %s", keys));
    }

    public List<String> getRequiredKeys() {
        return requiredKeys;
    }
}
