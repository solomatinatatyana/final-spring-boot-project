package ru.otus.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPriceVO {
    private String productName;
    private double price;
}
