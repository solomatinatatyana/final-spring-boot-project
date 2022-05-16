package ru.otus.finalproject.service.users;

import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    void updateUserById(long id, User user);
    User getUserByPhone(String phone);
    User getUserById(long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User getUserByName(String username);
    void deleteUserById(long id);
}
