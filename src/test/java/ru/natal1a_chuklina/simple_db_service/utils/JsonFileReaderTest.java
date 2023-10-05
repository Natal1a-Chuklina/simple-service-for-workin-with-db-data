package ru.natal1a_chuklina.simple_db_service.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class JsonFileReaderTest {

    private static final JsonFileReader reader = JsonFileReader.getInstance();

    @Test
    void read_WhenFileDoesNotExist_ThenThrowsIOException() {
        final String notExistingFileName = "NOT_EXISTING_FILE";

        assertThatExceptionOfType(IOException.class)
                .as("Must throw exception when file doesn't exist")
                .isThrownBy(() -> reader.read(Paths.get(notExistingFileName)))
                .withMessageContaining(String.format("Failed to read file %s.", notExistingFileName));
    }
}