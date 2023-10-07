package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.natal1a_chuklina.simple_db_service.utils.Constants;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SearchOutput extends Result {
    private final List<Result> results;

    public SearchOutput(List<Result> results) {
        super(Constants.SEARCH_TYPE);
        this.results = results;
    }
}
