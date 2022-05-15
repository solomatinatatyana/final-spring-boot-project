package ru.otus.finalproject.service.orders;

import ru.otus.finalproject.domain.*;

import java.util.List;

public interface OrderService {
    void createOrder(Order order, Car car, User user);

    void createOrderFromRequest(Request request);

    void updateOrderById(long id, Order order);
    Order getOrderById(long id);
    List<Order> getAllOrdersByCarBrand(String carBrand);
    List<Order> getAllOrdersByStatus(String status);
    List<Order> getAllOrdersByUserPhone(String phone);
    List<Order> getAllOrdersByUserId(Long userId);

    Order getOrderByCode(String code);
    List<Order> getAllOrders();
    void deleteOrderById(long id);

    double getTotalSum(List<Order> orders);
    void setStatusByOrderId(long id, String status);
}
