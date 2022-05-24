package ru.otus.finalproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import ru.otus.finalproject.domain.Role;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.repository.user.UserRepository;
import ru.otus.finalproject.service.users.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис UserService должен ")
@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @DisplayName("получать пользователя по его id")
    @Test
    public void shouldReturnUserById(){
        given(userRepository.findById(1L))
                .willReturn(Optional.of(
                        new User(1, "testUser","password","test@mail.ru",true, Role.ADMIN)));
        User actualUser = userService.getUserById(1L);
        assertThat(actualUser).isNotNull();
    }

    @DisplayName("получать всех пользователей")
    @Test
    public void shouldReturnAllUsers(){
        List<User> expectedUserList = Arrays.asList(
                new User(1, "testUser1","password","test1@mail.ru",true, Role.ADMIN),
                new User(2, "testUser2","password","test2@mail.ru",true, Role.USER),
                new User(2, "testUser3","password","test3@mail.ru",true, Role.MANAGER)

        );
        given(userRepository.findAll()).willReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUsers();
        assertThat(actualUserList.equals(expectedUserList));
    }

    @DisplayName("получать ошибку при запросе на несуществующего пользователя")
    @Test
    public void shouldThrowCommentException(){
        Throwable exception = assertThrows(UsernameNotFoundException.class,()->{
            given(userRepository.findById(2L)).willReturn(Optional.empty());
            userService.getUserById(2);
        });
        assertEquals("user with id [2] not found",exception.getMessage());
    }
}
