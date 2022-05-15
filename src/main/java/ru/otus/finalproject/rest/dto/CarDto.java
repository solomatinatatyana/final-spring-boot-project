package ru.otus.finalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private long id;
    @NotBlank(message = "Поле обязательно и не может быть пустым")
    private String brand;
    private List<OrderDto> orders;
    private Set<RequestDto> requests;

    public CarDto(long id, @NotBlank(message = "Поле обязательно и не может быть пустым") String brand) {
        this.id = id;
        this.brand = brand;
    }
}
