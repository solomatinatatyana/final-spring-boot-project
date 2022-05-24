package ru.otus.finalproject.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.Request;
import ru.otus.finalproject.domain.RequestStatus;
import ru.otus.finalproject.repository.requests.RequestRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.finalproject.domain.OrderStatus.NEW;

@DisplayName("Тест класса RequestRepository должен ")
@DataJpaTest
public class RequestRepositoryTest {

    private static final long EXISTING_FIRST_REQUEST_ID = 1L;
    private static final long EXISTING_SECOND_REQUEST_ID = 2L;


    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление новой заявки в БД")
    @Test
    public void shouldInsertNewRequest() {
        Car car = new Car(1, "testBrand");
        Product product = new Product("testProduct","descriptionTestProduct");

        Request expectedRequest = new Request(1, "testFirstName", RequestStatus.NEW.getRusName(),"79036787654",
                car, List.of(product),"testComment");

        requestRepository.saveAndFlush(expectedRequest);
        assertThat(expectedRequest.getId()).isGreaterThan(0);

        Request actualRequest = em.find(Request.class, expectedRequest.getId());
        assertThat(actualRequest).isNotNull()
                .matches(s -> !s.getFirstName().equals(""))
                .matches(s -> !s.getPhone().equals(""))
                .matches(s -> !s.getStatus().equals(""))
                .matches(s -> !s.getComment().equals(""))
                .matches(s -> s.getCar() != null)
                .matches(s -> s.getProducts() != null);
    }

    @DisplayName("проверять нахождение заявки по её id")
    @Test
    public void shouldReturnRequestById() {
        Request expectedRequest = em.find(Request.class, EXISTING_FIRST_REQUEST_ID);
        Request actualRequest = requestRepository.findById(expectedRequest.getId()).get();
        assertThat(actualRequest).usingRecursiveComparison().isEqualTo(expectedRequest);
    }

    @DisplayName("проверять нахождение заявок по status")
    @Test
    public void shouldReturnOrderByStatus() {
        Car car1 = new Car(1, "testBrand1");
        Product product1 = new Product("testProduct1","descriptionTestProduct1");

        Car car2 = new Car(1, "testBrand2");
        Product product2 = new Product("testProduct2","descriptionTestProduct2");

        Request request1 = new Request(1, "testFirstName1", RequestStatus.NEW.getRusName(),"79036787654",
                car1, List.of(product1),"testComment");

        Request request2 = new Request(1, "testFirstName2", RequestStatus.NEW.getRusName(),"79036787654",
                car2, List.of(product2),"testComment");


        List<Request> expectedRequestList = Arrays.asList(request1, request2);
        List<Request> actualRequestList = requestRepository.findRequestByStatus(NEW.getRusName());
        assertThat(CollectionUtils.isEqualCollection(actualRequestList, expectedRequestList));
    }

    @DisplayName("проверять нахождение заявок по phone")
    @Test
    public void shouldReturnOrderByPhone() {
        String testPhone = "79036787654";
        Car car1 = new Car(1, "testBrand1");
        Product product1 = new Product("testProduct1","descriptionTestProduct1");

        Car car2 = new Car(1, "testBrand2");
        Product product2 = new Product("testProduct2","descriptionTestProduct2");

        Request request1 = new Request(1, "testFirstName1", RequestStatus.NEW.getRusName(),testPhone,
                car1, List.of(product1),"testComment");

        Request request2 = new Request(1, "testFirstName2", RequestStatus.NEW.getRusName(),testPhone,
                car2, List.of(product2),"testComment");


        List<Request> expectedRequestList = Arrays.asList(request1, request2);
        List<Request> actualRequestList = requestRepository.findRequestByPhone(testPhone);
        assertThat(CollectionUtils.isEqualCollection(actualRequestList, expectedRequestList));
    }

    @DisplayName("проверять нахождение заявок по его firstName")
    @Test
    public void shouldReturnOrderByFirstName() {
        String testFirstName = "testFirstName";
        Car car1 = new Car(1, "testBrand1");
        Product product1 = new Product("testProduct1","descriptionTestProduct1");

        Car car2 = new Car(1, "testBrand2");
        Product product2 = new Product("testProduct2","descriptionTestProduct2");

        Request request1 = new Request(1, testFirstName, RequestStatus.NEW.getRusName(),"79036787654",
                car1, List.of(product1),"testComment");

        Request request2 = new Request(1, testFirstName, RequestStatus.NEW.getRusName(),"79036787654",
                car2, List.of(product2),"testComment");


        List<Request> expectedRequestList = Arrays.asList(request1, request2);
        List<Request> actualRequestList = requestRepository.findRequestByFirstName(testFirstName);
        assertThat(CollectionUtils.isEqualCollection(actualRequestList, expectedRequestList));
    }

    @DisplayName("проверять нахождение всех заявок")
    @Test
    public void shouldReturnExpectedRequestList() {
        List<Request> expectedRequestList = Arrays.asList(
                em.find(Request.class, EXISTING_FIRST_REQUEST_ID),
                em.find(Request.class, EXISTING_SECOND_REQUEST_ID)
        );
        List<Request> actualRequestList = requestRepository.findAll();
        assertThat(actualRequestList).containsAll(expectedRequestList);
    }

    @DisplayName("проверять удаление заявки по его id")
    @Test
    public void shouldDeleteCorrectRequestById() {
        Request secondRequest = em.find(Request.class, EXISTING_SECOND_REQUEST_ID);
        assertThat(secondRequest).isNotNull();
        em.detach(secondRequest);

        requestRepository.deleteById(EXISTING_SECOND_REQUEST_ID);
        Request deletedRequest = em.find(Request.class, EXISTING_SECOND_REQUEST_ID);

        assertThat(deletedRequest).isNull();
    }
}
