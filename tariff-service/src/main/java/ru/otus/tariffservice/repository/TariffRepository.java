package ru.otus.tariffservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.tariffservice.domain.Tariff;

public interface TariffRepository extends JpaRepository<Tariff,Long> {
    Tariff findByBrand(String brand);
}
