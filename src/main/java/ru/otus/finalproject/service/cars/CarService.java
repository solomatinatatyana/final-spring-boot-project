package ru.otus.finalproject.service.cars;

import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;

import java.util.List;

public interface CarService {
    void createCar(Car car);
    void updateCarById(long id, Car car);
    Car getCarById(long id);
    Car getCarByBrand(String carBrand);
    List<Car> getAllCars();
    void deleteCarById(long id);
}
