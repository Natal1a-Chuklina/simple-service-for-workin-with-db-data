package ru.natal1a_chuklina.simple_db_service.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileInteractor {
    private static JsonFileInteractor instance;

    private JsonFileInteractor() {
    }

    public static JsonFileInteractor getInstance() {
        if (instance == null) {
            instance = new JsonFileInteractor();
        }

        return instance;
    }

    public String read(Path path) throws IOException {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException | OutOfMemoryError e) {
            throw new FileNotFoundException(String.format("Failed to read file %s. MSG: %s", path, e.getMessage()));
        }
    }

    public void write(Path path, String outputValue) throws IOException {
        try {
            Files.write(path, outputValue.getBytes());
        } catch (IOException e) {
            throw new FileNotFoundException(String.format("Failed to write data to file %s. MSG: %s", path, e.getMessage()));
        }
    }

}
