package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Customer {
    private final String lastName;
    private final String firstName;
}
