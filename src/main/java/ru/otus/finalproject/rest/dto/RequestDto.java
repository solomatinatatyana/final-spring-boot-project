package ru.otus.finalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private long id;

    private String firstName;

    private String status;

    private String phone;

    private CarDto car;

    private List<ProductDto> products;

    private String comment;
}
