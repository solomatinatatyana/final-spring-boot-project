package ru.otus.finalproject.service.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.finalproject.domain.Role;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.exceptions.UserException;
import ru.otus.finalproject.repository.user.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void createUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setActive(true);
        user.setRoles(Role.USER);
        if(!userRepository.existsUserByUsername(user.getUsername())){
            userRepository.saveAndFlush(user);
        }else {
            throw new UserException("user with name ["+ user.getUsername() +"] is already exist!");
        }
    }

    @Transactional
    @Override
    public void updateUserById(long id, User user) {
        /*User userToBeUpdated = getProductById(id);
        userToBeUpdated.setProductName(product.getProductName());
        userToBeUpdated.setDescription(product.getDescription());
        userToBeUpdated.setPrice(product.getPrice());
        userToBeUpdated.saveAndFlush(productToBeUpdated);*/
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(()->new UserException("user with phone [" + phone + "] not found"));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(()->new UserException("user with id [" + id + "] not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new UserException("user with username [" + username + "] not found"));
    }

    @Transactional
    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
