package ru.otus.finalproject.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
}
