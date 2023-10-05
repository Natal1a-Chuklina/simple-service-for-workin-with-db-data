package ru.natal1a_chuklina.simple_db_service.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SearchInputDto {
    private List<Map<String, String>> criteria;
}
