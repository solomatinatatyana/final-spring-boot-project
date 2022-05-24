package ru.otus.finalproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс User")
public class UserTest {
    @DisplayName("Пользователь корректно создаётся конструктором")
    @Test()
    public void shouldHaveCorrectUserConstructor(){
        User user = new User(1, "testUser","password","test@mail.ru",true,Role.ADMIN);
        assertAll("user",
                ()-> assertEquals(1, user.getId()),
                ()-> assertEquals("testUser", user.getUsername()),
                ()-> assertEquals("password", user.getPassword()),
                ()-> assertEquals("test@mail.ru", user.getEmail()),
                ()-> assertTrue(user.isActive()),
                ()-> assertEquals(Role.ADMIN, user.getRoles()));
    }
}
