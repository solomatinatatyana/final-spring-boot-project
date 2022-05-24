package ru.otus.finalproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Order")
public class OrderTest {

    @DisplayName("Заказ корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectOrderConstructor(){
        Order order = new Order(1, "testCode",
                new Car(1, "testBrand"),
                new User(1, "testUser","password","test@mail.ru",true,Role.ADMIN));
        assertAll("order",
                ()-> assertEquals(1, order.getId()),
                ()-> assertEquals("testCode", order.getCode()),
                ()-> assertEquals(1,order.getCar().getId()),
                ()-> assertEquals(1, order.getUser().getId()),
                ()-> assertEquals("testBrand", order.getCar().getBrand()),
                ()-> assertEquals("testUser", order.getUser().getUsername()));
    }
}
