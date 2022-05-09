package ru.otus.finalproject.service.cars;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.repository.cars.CarRepository;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Transactional
    @Override
    public void createCar(Car car) {

    }

    @Transactional
    @Override
    public void updateCarById(long id, Car car) {

    }

    @Override
    public Car getCarById(long id) {
        return null;
    }

    @Override
    public Car getCarByBrand(String carBrand) {
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCarById(long id) {

    }
}
