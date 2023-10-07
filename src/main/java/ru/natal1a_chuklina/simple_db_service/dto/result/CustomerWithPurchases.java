package ru.natal1a_chuklina.simple_db_service.dto.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CustomerWithPurchases {
    private final String name;
    private final List<Purchase> purchases;
    private final double totalExpenses;
}
