package ru.otus.finalproject.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.repository.cars.CarRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Тест класса CarRepository должен ")
@DataJpaTest
public class CarRepositoryTest {
    private static final long EXISTING_FIRST_CAR_BRAND_ID = 1L;
    private static final String EXISTING_FIRST_CAR_BRAND = "Tayota";
    private static final long EXISTING_SECOND_CAR_BRAND_ID = 2L;
    private static final String EXISTING_SECOND_CAR_BRAND = "Mazda";

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление нового бренда автомобиля в БД")
    @Test
    public void shouldInsertNewCar() {
        Car expectedCar = new Car(3, "testBrand");
        carRepository.saveAndFlush(expectedCar);
        Car actualCar = carRepository.findById(expectedCar.getId()).get();
        assertThat(actualCar).usingRecursiveComparison().isEqualTo(expectedCar);
    }

    @DisplayName("проверять нахождение бренда автомобиля по его id")
    @Test
    public void shouldReturnCarById() {
        Car expectedCar = em.find(Car.class, EXISTING_FIRST_CAR_BRAND_ID);
        Car actualCar = carRepository.findById(expectedCar.getId()).get();
        assertThat(actualCar).usingRecursiveComparison().isEqualTo(expectedCar);
    }

    @DisplayName("проверять нахождение всех автомобилей")
    @Test
    public void shouldReturnExpectedCarsList() {
        Car c1 = new Car(EXISTING_FIRST_CAR_BRAND_ID, EXISTING_FIRST_CAR_BRAND);
        Car c2 = new Car(EXISTING_SECOND_CAR_BRAND_ID, EXISTING_SECOND_CAR_BRAND);

        List<Car> expectedCarList = Arrays.asList(c1, c2);
        List<Car> actualCarList = carRepository.findAll();
        assertThat(CollectionUtils.isEqualCollection(actualCarList, expectedCarList));
    }

    @DisplayName("проверять удаление бренда автомобиля по его id")
    @Test
    public void shouldDeleteCorrectCarById() {
        Car secondCar = em.find(Car.class, EXISTING_SECOND_CAR_BRAND_ID);
        assertThat(secondCar).isNotNull();
        em.detach(secondCar);

        carRepository.deleteById(EXISTING_SECOND_CAR_BRAND_ID);
        Car deletedCar = em.find(Car.class, EXISTING_SECOND_CAR_BRAND_ID);

        assertThat(deletedCar).isNull();
    }
}
