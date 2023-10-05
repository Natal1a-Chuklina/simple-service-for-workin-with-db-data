package ru.natal1a_chuklina.simple_db_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DBConfigurationDto {
    @JsonProperty("dbUrl")
    private String url;
    @JsonProperty("dbUsername")
    private String username;
    @JsonProperty("dbPassword")
    private String password;
}
