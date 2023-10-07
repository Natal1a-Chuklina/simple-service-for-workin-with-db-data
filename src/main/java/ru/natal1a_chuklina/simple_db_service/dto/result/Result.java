package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class Result {
    private final String type;
}
