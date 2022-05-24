package ru.otus.finalproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Product")
public class ProductTest {
    @DisplayName("Услуга корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectProductConstructor(){
        Product product = new Product("testProduct", "testProductDescription");
        assertAll("product",
                ()-> assertEquals("testProduct", product.getProductName()),
                ()-> assertEquals("testProductDescription", product.getDescription()));
    }
}
