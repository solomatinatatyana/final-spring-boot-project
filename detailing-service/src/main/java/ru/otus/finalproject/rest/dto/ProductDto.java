package ru.otus.finalproject.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
public class ProductDto {

    private long id;
    @NotBlank(message = "Поле обязательное и не может быть пустым")
    private String productName;

    private String description;

    @PositiveOrZero(message = "Цена должна быть неотрицательным числом")
    private Integer price;

    public ProductDto(long id, @NotBlank(message = "Поле обязательное и не может быть пустым") String productName,
                      String description,
                      @PositiveOrZero(message = "Цена должна быть неотрицательным числом") Integer price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}
