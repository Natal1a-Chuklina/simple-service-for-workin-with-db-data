package ru.natal1a_chuklina.simple_db_service.exception;

public class JsonParseException extends RuntimeException {
    public JsonParseException() {
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
