package ru.natal1a_chuklina.simple_db_service.command;

import picocli.CommandLine.ITypeConverter;
import ru.natal1a_chuklina.simple_db_service.utils.Constants;


public class OperationTypeConverter implements ITypeConverter<OperationType> {
    @Override
    public OperationType convert(String value) {
        switch (value) {
            case Constants.SEARCH_TYPE:
                return OperationType.SEARCH;
            case Constants.STAT_TYPE:
                return OperationType.STATISTICS;
            default:
                throw new IllegalArgumentException(String.format("Operation %s doesn't exist. Existing operations: %s.",
                        value,
                        OperationType.getAllOperationTypes()));
        }
    }

}
