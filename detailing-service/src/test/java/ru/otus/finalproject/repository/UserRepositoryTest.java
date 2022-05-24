package ru.otus.finalproject.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.finalproject.domain.Role;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.repository.user.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса UserRepository должен ")
@DataJpaTest
public class UserRepositoryTest {
    private static final long EXISTING_FIRST_USER_ID = 1L;
    private static final long EXISTING_SECOND_USER_ID = 2L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager em;


    @DisplayName("проверять добавление нового пользователя в БД")
    @Test
    public void shouldInsertNewUser() {
        User expectedUser = new User(1, "testUser","password","test@mail.ru",true, Role.ADMIN);
        userRepository.saveAndFlush(expectedUser);
        User actualUser = userRepository.findById(expectedUser.getId()).get();
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(actualUser);
    }

    @DisplayName("проверять нахождение пользователя по его id")
    @Test
    public void shouldReturnUserById() {
        User expectedUser = em.find(User.class, EXISTING_FIRST_USER_ID);
        User actualUser = userRepository.findById(expectedUser.getId()).get();
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @DisplayName("проверять нахождение всех комментриев")
    @Test
    public void shouldReturnExpectedUsersList() {
        List<User> expectedUsersList = Arrays.asList(
                new User(1, "testAdmin","password","admin@mail.ru",true, Role.ADMIN),
                new User(1, "testManager","password","manager@mail.ru",true, Role.MANAGER),
                new User(1, "testUser","password","user@mail.ru",true, Role.USER)
        );
        List<User> actualUsersList = userRepository.findAll();
        assertThat(assertThat(CollectionUtils.isEqualCollection(actualUsersList, expectedUsersList)));
    }

    @DisplayName("проверять удаление пользователя по его id")
    @Test
    public void shouldDeleteCorrectUserById() {
        User secondUser = em.find(User.class, EXISTING_SECOND_USER_ID);
        assertThat(secondUser).isNotNull();
        em.detach(secondUser);

        userRepository.deleteById(EXISTING_SECOND_USER_ID);
        User deletedUser = em.find(User.class, EXISTING_SECOND_USER_ID);

        assertThat(deletedUser).isNull();
    }
}
