package ru.natal1a_chuklina.simple_db_service.command;

import ru.natal1a_chuklina.simple_db_service.dto.CommandInputDto;

public interface CommandResultListener {
    void update(CommandInputDto inputDto);
}
