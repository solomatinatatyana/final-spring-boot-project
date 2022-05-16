package ru.otus.finalproject.service.cars;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.exceptions.CarException;
import ru.otus.finalproject.exceptions.ProductException;
import ru.otus.finalproject.repository.cars.CarRepository;

import java.util.List;

@Transactional
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Transactional
    @Override
    public void createCar(Car car) {
        if(!carRepository.existsCarByBrand(car.getBrand())){
            carRepository.saveAndFlush(car);
        }else {
            throw new ProductException("car with brand ["+ car.getBrand() +"] is already exist!");
        }
    }

    @Transactional
    @Override
    public void updateCarById(long id, Car car) {
        Car carToBeUpdated = getCarById(id);
        carToBeUpdated.setBrand(car.getBrand());
        carRepository.saveAndFlush(carToBeUpdated);
    }

    @Override
    public Car getCarById(long id) {
        return carRepository.findById(id).orElseThrow(()->new CarException("car with id [" + id + "] not found"));
    }

    @Override
    public Car getCarByBrand(String carBrand) {
        return carRepository.findByBrand(carBrand).orElseThrow(()->new CarException("car with brand [" + carBrand + "] not found"));
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCarById(long id) {
        carRepository.deleteById(id);
    }
}
