package ru.otus.finalproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.Request;
import ru.otus.finalproject.domain.RequestStatus;
import ru.otus.finalproject.exceptions.RequestException;
import ru.otus.finalproject.repository.requests.RequestRepository;
import ru.otus.finalproject.service.requests.RequestServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис RequestService должен ")
@SpringBootTest
public class RequestServiceImplTest {
    @MockBean
    private RequestRepository requestRepository;
    @Autowired
    private RequestServiceImpl requestService;

    @DisplayName("получать заявку по имени")
    @Test
    public void shouldReturnRequestByFirstName(){
        List<Request> expectedRequestList = Arrays.asList(
                new Request(1, "testFirstName1", RequestStatus.NEW.getRusName(),"79036787654",
                        new Car(1, "testCarBrand"),
                        List.of(new Product("testProduct1","descriptionTestProduct")),"testComment"),
                new Request(1, "testFirstName2", RequestStatus.NEW.getRusName(),"79036787654",
                        new Car(1, "testCar"),
                        List.of(new Product("testProduct2","descriptionTestProduct")),"testComment"));
        List<Request> actualRequestList = requestService.getAllRequestsByCustomerName("testFirstName1");
        assertThat(actualRequestList.equals(expectedRequestList));
    }

    @DisplayName("получать заказ по его id")
    @Test
    public void shouldReturnRequestById(){
        given(requestRepository.findById(1L)).willReturn(Optional.of(new Request(1, "testFirstName1", RequestStatus.NEW.getRusName(),"79036787654",
                new Car(1, "testCarBrand"),
                List.of(new Product("testProduct1","descriptionTestProduct")),"testComment")));
        Request actualRequest = requestService.getRequestById(1);
        assertThat(actualRequest).isNotNull();
    }

    @DisplayName("получать все заказы")
    @Test
    public void shouldReturnAllRequests(){
        List<Request> expectedRequestList = Arrays.asList(
                new Request(1, "testFirstName1", RequestStatus.NEW.getRusName(),"79036787654",
                        new Car(1, "testCarBrand"),
                        List.of(new Product("testProduct1","descriptionTestProduct")),"testComment"),
                new Request(1, "testFirstName2", RequestStatus.NEW.getRusName(),"79036787654",
                        new Car(1, "testCar"),
                        List.of(new Product("testProduct2","descriptionTestProduct")),"testComment"));
        given(requestRepository.findAll()).willReturn(expectedRequestList);
        List<Request> actualRequestList = requestService.getAllRequests();
        assertThat(actualRequestList.equals(expectedRequestList));
    }

    @DisplayName("получать ошибку при запросе на несуществующую заявку")
    @Test
    public void shouldThrowRequestException(){
        Throwable exception = assertThrows(RequestException.class,()->{
            given(requestRepository.findById(2L)).willReturn(Optional.empty());
            requestService.getRequestById(2);
        });
        assertEquals("request with id [2] not found",exception.getMessage());
    }
}
