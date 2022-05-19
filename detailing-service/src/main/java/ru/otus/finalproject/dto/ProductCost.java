package ru.otus.finalproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.finalproject.domain.Product;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductCost {

    @NotBlank(message = "Необходимо выбрать марку")
    private String brand;
    private List<Product> products;
    private double cost;
}
