package ru.otus.finalproject.repository.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Order;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    Optional<Car> findByBrand(String brand);
}
