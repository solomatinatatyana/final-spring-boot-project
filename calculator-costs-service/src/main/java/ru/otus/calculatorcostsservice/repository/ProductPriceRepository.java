package ru.otus.calculatorcostsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.calculatorcostsservice.domain.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice,Long> {
    ProductPrice findFirstByProductName(String productName);
}
