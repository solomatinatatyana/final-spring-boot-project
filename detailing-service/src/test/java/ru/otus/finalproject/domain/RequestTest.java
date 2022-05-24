package ru.otus.finalproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.finalproject.domain.RequestStatus.NEW;

@DisplayName("Класс Request")
public class RequestTest {

    @DisplayName("Заявка корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectRequestConstructor(){
        Request request = new Request(1, "testFirstName", NEW.getRusName(),"79036787654",
                new Car(1, "testAuthor"),
                List.of(new Product("testProduct","descriptionTestProduct")),"testComment");
        assertAll("request",
                ()-> assertEquals(1, request.getId()),
                ()-> assertEquals("testFirstName", request.getFirstName()),
                ()-> assertEquals(NEW.getRusName(), request.getStatus()),
                ()-> assertEquals("79036787654", request.getPhone()),
                ()-> assertEquals(1,request.getCar().getId()),
                ()-> assertEquals(1, request.getProducts().size()),
                ()-> assertEquals("testComment",request.getComment()));
    }
}
