package ru.otus.finalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.finalproject.domain.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private long id;

    private String code;

    private CarDto car;

    private String status;

    private Double total;

    private long requestId;

    private User user;

    private String customerName;

    private List<ProductDto> products;
}
