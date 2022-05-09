package ru.otus.finalproject.service.orders;

import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Product;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);
    void updateOrderById(long id, Order order);
    Order getOrderById(long id);
    List<Order> getAllOrdersByCarBrand(String carBrand);
    List<Order> getAllOrdersByStatus(String status);
    Order getOrderByCode(String code);
    List<Order> getAllOrders();
    void deleteOrderById(long id);
}
