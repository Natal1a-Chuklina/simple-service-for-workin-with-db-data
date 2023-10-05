package ru.natal1a_chuklina.simple_db_service.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileReader {
    private static JsonFileReader instance;

    private JsonFileReader() {
    }

    public static JsonFileReader getInstance() {
        if (instance == null) {
            instance = new JsonFileReader();
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

}
