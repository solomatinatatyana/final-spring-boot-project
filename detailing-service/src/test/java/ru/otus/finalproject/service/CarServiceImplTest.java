package ru.otus.finalproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.exceptions.CarException;
import ru.otus.finalproject.repository.cars.CarRepository;
import ru.otus.finalproject.service.cars.CarServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис CarService должен ")
@SpringBootTest
public class CarServiceImplTest {

    @MockBean
    private CarRepository carRepository;
    @Autowired
    private CarServiceImpl carService;


    @DisplayName("получать бренды автомобилей по его id")
    @Test
    public void shouldReturnCarById(){
        given(carRepository.findById(1L)).willReturn(Optional.of(new Car(1,"testBrand")));
        Car actualCar = carService.getCarById(1);
        assertThat(actualCar).isNotNull();
    }

    @DisplayName("получать все бренды автомобилей")
    @Test
    public void shouldReturnAllCars(){
        List<Car> expectedCarList = Arrays.asList(
                new Car(1,"testBrand1"),
                new Car(2,"testBrand2"));
        given(carRepository.findAll()).willReturn(expectedCarList);
        List<Car> actualCarList = carService.getAllCars();
        assertThat(actualCarList.equals(expectedCarList));
    }

    @DisplayName("получать ошибку при запросе на несуществующий бренд автомобиля")
    @Test
    public void shouldThrowCarException(){
        Throwable exception = assertThrows(CarException.class,()->{
            given(carRepository.findById(2L)).willReturn(Optional.empty());
            carService.getCarById(2);
        });
        assertEquals("Car with id [2] not found",exception.getMessage());
    }
}
