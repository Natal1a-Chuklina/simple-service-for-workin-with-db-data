package ru.natal1a_chuklina.simple_db_service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DtoMapper {
    private final ObjectMapper mapper;
    private static DtoMapper instance;

    private DtoMapper() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public <T> T stringToDto(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(String.format("Failed to parse string. MSG: %s", e.getMessage()));
        }
    }

    public static DtoMapper getInstance() {
        if (instance == null) {
            instance = new DtoMapper();
        }

        return instance;
    }
}
