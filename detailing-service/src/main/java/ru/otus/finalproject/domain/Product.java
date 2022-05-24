package ru.otus.finalproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
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

    public Product(long id, String productName, String description, double price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public Product(String productName, String description) {
        this.productName = productName;
        this.description = description;
    }
}
