package ru.otus.finalproject.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Role;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.repository.orders.OrderRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.finalproject.domain.OrderStatus.NEW;

@DisplayName("Тест класса OrderRepository должен ")
@DataJpaTest
public class OrderRepositoryTest {

    private static final long EXISTING_FIRST_ORDER_ID = 1L;
    private static final long EXISTING_SECOND_ORDER_ID = 2L;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление нового заказа в БД")
    @Test
    public void shouldInsertNewOrder() {
        Car car = new Car(1, "testBrand");
        User user = new User(1, "testUser","password","test@mail.ru",true, Role.ADMIN);

        Order expectedOrder = new Order(1, "testCode", car, user);
        orderRepository.saveAndFlush(expectedOrder);
        assertThat(expectedOrder.getId()).isGreaterThan(0);

        Order actualOrder = em.find(Order.class, expectedOrder.getId());
        assertThat(actualOrder).isNotNull().matches(s -> !s.getCode().equals(""))
                .matches(s -> s.getCar() != null)
                .matches(s -> s.getUser() != null);
    }

    @DisplayName("проверять нахождение заказа по её id")
    @Test
    public void shouldReturnOrderById() {
        Order expectedOrder = em.find(Order.class, EXISTING_FIRST_ORDER_ID);
        Order actualOrder = orderRepository.findById(expectedOrder.getId()).get();
        assertThat(actualOrder).usingRecursiveComparison().isEqualTo(expectedOrder);
    }

    @DisplayName("проверять нахождение заказа по его code")
    @Test
    public void shouldReturnOrderByCode() {
        Order expectedOrder = em.find(Order.class, EXISTING_FIRST_ORDER_ID);
        Order actualOrder = orderRepository.findOrderByCode(expectedOrder.getCode()).get();
        assertThat(actualOrder).usingRecursiveComparison().isEqualTo(expectedOrder);
    }

    @DisplayName("проверять нахождение заказов по его status")
    @Test
    public void shouldReturnOrderByStatus() {
        Order order1 = new Order(1, "testCode1",
                new Car(1, "testBrand1"),
                new User(1, "testUser1","password","test@mail.ru",true,Role.ADMIN));
        Order order2 = new Order(1, "testCode2",
                new Car(1, "testBrand2"),
                new User(1, "testUser2","password","test@mail.ru",true,Role.ADMIN));
        List<Order> expectedOrderList = Arrays.asList(order1, order2);
        List<Order> actualOrderList = orderRepository.findOrderByStatus(NEW.getRusName());
        assertThat(CollectionUtils.isEqualCollection(expectedOrderList, expectedOrderList));
    }

    @DisplayName("проверять нахождение всех заказов")
    @Test
    public void shouldReturnExpectedOrdersList() {
        List<Order> expectedOrderList = Arrays.asList(
                em.find(Order.class, EXISTING_FIRST_ORDER_ID),
                em.find(Order.class, EXISTING_SECOND_ORDER_ID)
        );
        List<Order> actualOrderList = orderRepository.findAll();
        assertThat(actualOrderList).containsAll(expectedOrderList);
    }

    @DisplayName("проверять удаление заказа по его id")
    @Test
    public void shouldDeleteCorrectOrderById() {
        Order secondOrder = em.find(Order.class, EXISTING_SECOND_ORDER_ID);
        assertThat(secondOrder).isNotNull();
        em.detach(secondOrder);

        orderRepository.deleteById(EXISTING_SECOND_ORDER_ID);
        Order deletedOrder = em.find(Order.class, EXISTING_SECOND_ORDER_ID);

        assertThat(deletedOrder).isNull();
    }
}
