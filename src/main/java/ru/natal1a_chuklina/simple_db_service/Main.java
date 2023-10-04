package ru.natal1a_chuklina.simple_db_service;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import ru.natal1a_chuklina.simple_db_service.command.DBInteractionCommand;

@Slf4j
public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new DBInteractionCommand()).execute(args);
        System.exit(exitCode);
    }
}