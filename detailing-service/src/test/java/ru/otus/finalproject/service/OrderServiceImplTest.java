package ru.otus.finalproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.finalproject.domain.*;
import ru.otus.finalproject.exceptions.OrderException;
import ru.otus.finalproject.repository.orders.OrderRepository;
import ru.otus.finalproject.service.orders.OrderServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static ru.otus.finalproject.domain.OrderStatus.NEW;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис OrderService должен ")
@SpringBootTest
public class OrderServiceImplTest {
    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private OrderServiceImpl orderService;

    @DisplayName("получать заказ по его коду")
    @Test
    public void shouldReturnOrderByCode(){
        given(orderRepository.findOrderByCode("testCode")).willReturn(Optional.of(new Order(1, "testCode",
                new Car(1, "testBrand"),
                new User(1, "testUser","password","test@mail.ru",true, Role.ADMIN))));
        Order actualOrder = orderService.getOrderByCode("testCode");
        assertThat(actualOrder).isNotNull();
    }

    @DisplayName("получать заказ по его id")
    @Test
    public void shouldReturnOrderById(){
        given(orderRepository.findById(1L)).willReturn(Optional.of(new Order(1, "testCode",
                new Car(1, "testBrand"),
                new User(1, "testUser","password","test@mail.ru",true, Role.ADMIN))));
        Order actualOrder = orderService.getOrderById(1);
        assertThat(actualOrder).isNotNull();
    }

    @DisplayName("получать все заказы")
    @Test
    public void shouldReturnAllOrders(){
        List<Order> expectedOrderList = Arrays.asList(
                new Order(1, "testCode1",
                        new Car(1, "testBrand1"),
                        new User(1, "testUser1","password","test@mail.ru",true, Role.ADMIN)),
                new Order(1, "testCode2",
                        new Car(1, "testBrand3"),
                        new User(1, "testUser2","password","test2@mail.ru",true, Role.ADMIN)));
        given(orderRepository.findAll()).willReturn(expectedOrderList);
        List<Order> actualOrderList = orderService.getAllOrders();
        assertThat(actualOrderList.equals(expectedOrderList));
    }

    @DisplayName("получать ошибку при запросе на несуществующий заказ")
    @Test
    public void shouldThrowOrderException(){
        Throwable exception = assertThrows(OrderException.class,()->{
            given(orderRepository.findById(2L)).willReturn(Optional.empty());
            orderService.getOrderById(2);
        });
        assertEquals("Order with id [2] not found",exception.getMessage());
    }

    @DisplayName("получать все заказы по бренду автомобиля")
    @Test
    public void shouldReturnAllOrdersByCarBrand(){
        List<Order> expectedOrderList = Arrays.asList(
                new Order(1, "testCode1",
                        new Car(1, "testBrand1"),
                        new User(1, "testUser1","password","test@mail.ru",true, Role.ADMIN)));
        given(orderRepository.findAll()).willReturn(expectedOrderList);
        List<Order> actualOrderList = orderService.getAllOrdersByCarBrand("testBrand1");
        assertThat(actualOrderList.equals(expectedOrderList));
        assertThat(actualOrderList).hasSize(1);
    }

    @DisplayName("получать все заказы по статусу")
    @Test
    public void shouldReturnAllOrdersByStatus(){
        List<Order> expectedOrderList = Arrays.asList(
                new Order(1, "testCode1",
                        new Car(1, "testBrand1"),
                        new User(1, "testUser1","password","test@mail.ru",true, Role.ADMIN)));
        given(orderRepository.findAll()).willReturn(expectedOrderList);
        List<Order> actualOrderList = orderService.getAllOrdersByStatus(NEW.getRusName());
        assertThat(actualOrderList.equals(expectedOrderList));
        assertThat(actualOrderList).hasSize(1);
    }

    @DisplayName("получать общую сумму по всем услугам")
    @Test
    public void shouldReturnOrderTotalSum(){
        Product product1 = new Product(1,"testProduct1", "testProductDescription1",1000);
        Product product2 = new Product(2,"testProduct2", "testProductDescription2",2000);
        List<Product> products = List.of(product1,product2);
        double total = orderService.getTotalSum(products);
        assertEquals(3000, total);
    }
}
