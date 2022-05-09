package ru.otus.finalproject.service.orders;

import org.springframework.stereotype.Service;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.OrderStatus;
import ru.otus.finalproject.exceptions.OrderException;
import ru.otus.finalproject.repository.cars.CarRepository;
import ru.otus.finalproject.repository.orders.OrderRepository;
import ru.otus.finalproject.repository.products.ProductRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CarRepository carRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void createOrder(Order order) {
        order.setStatus(OrderStatus.NEW.getRusName());
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void updateOrderById(long id, Order order) {
        Order orderToBeUpdated = getOrderById(id);
        orderToBeUpdated.setCode(order.getCode());
        orderToBeUpdated.setStatus(order.getStatus());
        orderToBeUpdated.setTotal(order.getTotal());
        orderToBeUpdated.setCar(carRepository.findByBrand(order.getCar().getBrand()).get());
        //orderToBeUpdated.setCarBrand(order.getCarBrand());
        //orderToBeUpdated.setRequestId(order.getRequestId());

        //orderToBeUpdated.setProducts();

        orderRepository.saveAndFlush(orderToBeUpdated);
    }

    @Override
    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElseThrow(()->new OrderException("order with id [" + id + "] not found"));
    }

    @Override
    public List<Order> getAllOrdersByCarBrand(String carBrand) {
        return carRepository.findByBrand(carBrand).get().getOrders();
    }

    @Override
    public List<Order> getAllOrdersByStatus(String status) {
        return orderRepository.findOrderByStatus(status);
    }

    @Override
    public Order getOrderByCode(String code) {
        return orderRepository.findOrderByCode(code).orElseThrow(()->new OrderException("Order with code [" + code + "] not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }
}
