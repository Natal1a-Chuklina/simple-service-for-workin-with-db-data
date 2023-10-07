package ru.natal1a_chuklina.simple_db_service.command;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.natal1a_chuklina.simple_db_service.dto.CommandInput;

import java.util.ArrayList;
import java.util.List;

@Command(mixinStandardHelpOptions = true)
@Slf4j
public class DBInteractionCommand implements Runnable {
    private final List<CommandResultListener> listeners = new ArrayList<>();
    @Option(names = {"-t", "--type"}, description = "Operation type", required = true, converter = OperationTypeConverter.class)
    private OperationType operation;

    @Option(names = {"-i", "--input"}, description = "Input file name", required = true)
    private String inputFile;

    @Option(names = {"-o", "--output"}, description = "Output file name", required = true)
    private String outputFile;

    @Option(names = {"-v", "--verbose"}, description = "Verbose log")
    private boolean isVerbose;

    @Override
    public void run() {
        if (isVerbose) {
            changeLoggingLevel(Level.INFO);
        }

        log.info("Command processing.");
        log.info("Operation type: " + operation.getName());
        log.info("Input file name: " + inputFile);
        log.info("Output file name: " + outputFile);
        log.info("Is verbose: " + isVerbose);

        notifyListeners();
    }

    private void changeLoggingLevel(Level level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();
        loggerList.forEach(logger -> logger.setLevel(level));
    }

    public void addListener(CommandResultListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(CommandResultListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    private void notifyListeners() {
        CommandInput inputDto = new CommandInput(operation, inputFile, outputFile);
        listeners.forEach(listener -> listener.update(inputDto));
    }
}
