package ru.otus.finalproject.repository.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Product;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByProductName(String productName);
}
