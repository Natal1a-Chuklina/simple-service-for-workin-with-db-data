package ru.natal1a_chuklina.simple_db_service;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import ru.natal1a_chuklina.simple_db_service.command.DBInteractionCommand;
import ru.natal1a_chuklina.simple_db_service.dto.CommandInput;
import ru.natal1a_chuklina.simple_db_service.dto.DtoMapper;
import ru.natal1a_chuklina.simple_db_service.service.DBInteractionService;
import ru.natal1a_chuklina.simple_db_service.utils.JsonFileInteractor;

@Slf4j
public class Main {
    public static void main(String[] args) {
        final CommandInput[] inputDto = new CommandInput[1];
        DBInteractionCommand dbInteractionCommand = new DBInteractionCommand();
        dbInteractionCommand.addListener(dto -> inputDto[0] = dto);
        new CommandLine(dbInteractionCommand).execute(args);

        JsonFileInteractor fileInteractor = JsonFileInteractor.getInstance();
        DtoMapper mapper = DtoMapper.getInstance();
        DBInteractionService service = new DBInteractionService(fileInteractor, mapper, inputDto[0]);
        service.execute();
    }
}