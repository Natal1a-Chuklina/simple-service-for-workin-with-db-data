package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.natal1a_chuklina.simple_db_service.utils.Constants;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class StatOutput extends Result {
    private int totalDays;
    private List<CustomerWithPurchases> customers;
    private double totalExpenses;
    private double avgExpenses;

    public StatOutput() {
        super(Constants.STAT_TYPE);
    }
}
