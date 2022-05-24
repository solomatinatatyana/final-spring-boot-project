package ru.otus.finalproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Car")
public class CarTest {
    @DisplayName("Машина корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectCarConstructor(){
        Car car = new Car(1, "testBrand");
        assertAll("car",
                ()-> assertEquals(1, car.getId()),
                ()-> assertEquals("testBrand", car.getBrand()));
    }
}
