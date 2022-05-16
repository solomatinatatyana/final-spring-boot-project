package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Поле обязательное и не может быть пустым")
    @Column(name = "product_name",nullable = false, unique = true)
    private String productName;

    @Column(name = "description")
    private String description;

    @PositiveOrZero(message = "Цена должна быть неотрицательным числом")
    @Column(name = "price")
    private double price;

    public Product(long id) {
        this.id = id;
    }

    public Product(String productName, String description) {
        this.productName = productName;
        this.description = description;
    }
}
