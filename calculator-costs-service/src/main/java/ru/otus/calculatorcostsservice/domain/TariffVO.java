package ru.otus.calculatorcostsservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TariffVO {

    private String brand;
    private BigDecimal tariff;
}
