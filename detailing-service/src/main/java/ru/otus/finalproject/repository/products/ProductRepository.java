package ru.otus.finalproject.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByProductName(String productName);
    boolean existsProductByProductName(String productName);
}
