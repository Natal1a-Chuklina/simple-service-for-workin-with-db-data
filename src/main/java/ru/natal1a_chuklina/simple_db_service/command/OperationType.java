package ru.natal1a_chuklina.simple_db_service.command;

import ru.natal1a_chuklina.simple_db_service.utils.Constants;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum OperationType {
    SEARCH(Constants.SEARCH_OPERATION_TYPE),
    STATISTICS(Constants.STAT_OPERATION_TYPE);
    private final String name;

    OperationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getAllOperationTypes() {
        return Arrays.stream(OperationType.values())
                .map(OperationType::getName)
                .collect(Collectors.joining(", "));
    }
}
