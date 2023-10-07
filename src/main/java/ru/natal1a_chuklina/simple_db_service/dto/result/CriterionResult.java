package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.natal1a_chuklina.simple_db_service.utils.Constants;

import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CriterionResult extends Result {
    private final Map<String, String> criterion;
    private final List<Customer> results;

    public CriterionResult(Map<String, String> criterion, List<Customer> results) {
        super(Constants.CRITERION_RESULT_TYPE);
        this.criterion = criterion;
        this.results = results;
    }
}
