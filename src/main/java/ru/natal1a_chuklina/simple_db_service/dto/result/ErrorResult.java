package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.natal1a_chuklina.simple_db_service.utils.Constants;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ErrorResult extends Result {
    private final String message;

    public ErrorResult(String message) {
        super(Constants.ERROR_TYPE);
        this.message = message;
    }
}
