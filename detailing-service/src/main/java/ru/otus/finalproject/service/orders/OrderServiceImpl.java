package ru.otus.finalproject.service.orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.finalproject.domain.*;
import ru.otus.finalproject.exceptions.OrderException;
import ru.otus.finalproject.repository.cars.CarRepository;
import ru.otus.finalproject.repository.orders.OrderRepository;
import ru.otus.finalproject.repository.products.ProductRepository;
import ru.otus.finalproject.repository.user.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CarRepository carRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(Order order, Car car, User user) {
        order.setCode(UUID.randomUUID().toString().replace("-",""));
        order.setCar(car);
        order.setUser(user);
        order.setProducts(order.getProducts());//сделать проверку на фронте количество выбранных услуг иначе не создавать
        order.setTotal(getTotalSum(order.getProducts()));//расчитывается в зависимости от стоимостей и количества услуг
        order.setStatus(OrderStatus.NEW.getRusName());
        order.setRequestId(0);
        if(!orderRepository.existsOrderByCode(order.getCode())){
            orderRepository.saveAndFlush(order);
        }else {
            throw new OrderException("order with code ["+ order.getCode() +"] is already exist!");
        }
    }

    @Override
    public void createOrderFromRequest(Request request) {
        Order order = new Order();
        order.setCode(UUID.randomUUID().toString().replace("-",""));
        order.setCar(request.getCar());
        order.setCustomerName(request.getFirstName());
        order.setRequestId(request.getId());
        List<Product> products = List.copyOf(request.getProducts());
        order.setProducts(products);//сделать проверку на фронте количество выбранных услуг иначе не создавать
        order.setTotal(getTotalSum(request.getProducts()));//расчитывается в зависимости от стоимостей и количества услуг
        order.setStatus(OrderStatus.NEW.getRusName());
        if(!orderRepository.existsOrderByCode(order.getCode())){
            orderRepository.saveAndFlush(order);
        }else {
            throw new OrderException("order with code ["+ order.getCode() +"] is already exist!");
        }
    }

    @Override
    public void updateOrderById(long id, Order order) {
        Order orderToBeUpdated = getOrderById(id);
        orderToBeUpdated.setCode(order.getCode());
        orderToBeUpdated.setStatus(order.getStatus());
        orderToBeUpdated.setTotal(getTotalSum(order.getProducts()));
        orderToBeUpdated.setCar(carRepository.findByBrand(order.getCar().getBrand()).get());

        Iterable<Long> productsIds = order.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        orderToBeUpdated.setProducts(productRepository.findAllById(productsIds));

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
    public List<Order> getAllOrdersByUserPhone(String phone) {
        return userRepository.findByPhone(phone).get().getOrders();
    }

    @PostFilter("filterObject.user.username == authentication.principal.username")
    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAll();
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

    @Override
    public double getTotalSum(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    @Override
    public void setStatusByOrderId(long id, String status) {
        Order order = this.getOrderById(id);
        order.setStatus(status);
        this.updateOrderById(id,order);
    }
}
