package ru.natal1a_chuklina.simple_db_service.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.natal1a_chuklina.simple_db_service.command.OperationType;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CommandInputDto {
    private final OperationType operation;

    private final String inputFile;

    private final String outputFile;
}
