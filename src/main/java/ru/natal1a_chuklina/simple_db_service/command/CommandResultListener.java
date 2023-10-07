package ru.natal1a_chuklina.simple_db_service.command;

import ru.natal1a_chuklina.simple_db_service.dto.CommandInput;

public interface CommandResultListener {
    void update(CommandInput inputDto);
}
