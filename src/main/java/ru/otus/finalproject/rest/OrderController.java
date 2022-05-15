package ru.otus.finalproject.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.rest.dto.OrderDto;
import ru.otus.finalproject.rest.mappers.CarMapper;
import ru.otus.finalproject.rest.mappers.OrderMapper;
import ru.otus.finalproject.rest.mappers.ProductMapper;
import ru.otus.finalproject.service.cars.CarService;
import ru.otus.finalproject.service.orders.OrderService;
import ru.otus.finalproject.service.products.ProductService;
import ru.otus.finalproject.service.users.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static ru.otus.finalproject.domain.OrderStatus.CANCELED;
import static ru.otus.finalproject.domain.OrderStatus.DONE;

@Slf4j
@Controller
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final CarService carService;
    private final UserService userService;

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final CarMapper carMapper;


    public OrderController(OrderService orderService, ProductService productService, CarService carService, UserService userService,
                           OrderMapper orderMapper, ProductMapper productMapper, CarMapper carMapper) {
        this.orderService = orderService;
        this.productService = productService;
        this.carService = carService;
        this.userService = userService;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.carMapper = carMapper;
    }

    @GetMapping(value = "/order")
    public String getOrders(
            @RequestParam(required = false, name = "code") String code,
            @RequestParam(required = false, name = "brand") String brand,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "phone") String phone,
            @RequestParam(required = false, name = "userId") Long userId,

            Model model){
        List<Order> orders;
        if(code!=null && !code.isEmpty()){
            orders = Collections.singletonList(orderService.getOrderByCode(code));
        }else if(brand!=null && !brand.isEmpty()){
            orders = orderService.getAllOrdersByCarBrand(brand);
        } else if(status!=null && !status.isEmpty()){
        orders = orderService.getAllOrdersByStatus(status);
        }else if(phone!=null && !phone.isEmpty()) {
            orders = orderService.getAllOrdersByUserPhone(phone);
        }else if(userId!=null && userId!=0) {
            orders = orderService.getAllOrdersByUserId(userId);
        }else {
            //orders = Collections.emptyList();
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping(value = "/order/my-orders")
    public String getMyOrders(Model model){
        List<Order> orders;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByName(username);
        orders = orderService.getAllOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "my-order-list";
    }


    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
    @GetMapping(value = "/order/{id}/edit")
    public String editOrder(@PathVariable("id") long id, Model model){
        Order order = orderService.getOrderById(id);
        List<Product> productList = productService.getAllProducts();
        List<Car> carList = carService.getAllCars();
        model.addAttribute("order", order);
        model.addAttribute("cars", carList);
        model.addAttribute("products",productList);
        return "order-edit";
    }

    //@Timed(PATCH_AUTHOR_REQ_TIME)
    @PatchMapping(value = "/order/{id}")
    public String saveOrder(@PathVariable("id") long id,
                            @ModelAttribute("order") @Valid OrderDto orderDto, BindingResult result){
        //authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        if(result.hasErrors()){
            return "order-edit";
        }
        orderService.updateOrderById(id, orderMapper.toOrder(orderDto));
        return "redirect:/order";
    }

    @GetMapping(value = "/order/add")
    public String showAddOrderForm(Model model){
        List<Product> productList = productService.getAllProducts();
        List<Car> carList = carService.getAllCars();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.getUserByName(username);

        model.addAttribute("user", user);
        model.addAttribute("cars", carList);
        model.addAttribute("products",productList);
        model.addAttribute("order",new Order());
        return "order-add";
    }

    //@Timed(CREATE_AUTHOR_REQ_TIME)
    @PostMapping(value = "/order")
    public String createOrder( @Valid @ModelAttribute("order") Order order,
                              String brand,
                              String username,
                              BindingResult result, Model model){
        //authorService.insertAuthor(authorMapper.toAuthor(author));
        if(result.hasErrors()){
            return "order-add";
        }

        Car car1 = carService.getCarByBrand(brand);
        User user = userService.getUserByName(username);

        orderService.createOrder(order, car1,user);
        return "redirect:/order/my-orders";
    }

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    @DeleteMapping(value = "/order/{id}")
    public String deleteOrder(@PathVariable("id") long id){
        orderService.deleteOrderById(id);
        return "redirect:/order";
    }

    @PostMapping(value = "/order/{id}/cancel")
    public String cancelOrder(@PathVariable("id") long id){
        orderService.setStatusByOrderId(id, CANCELED.getRusName());
        return "redirect:/order";
    }

    @PostMapping(value = "/order/{id}/close")
    public String closeOrder(@PathVariable("id") long id){
        orderService.setStatusByOrderId(id, DONE.getRusName());
        return "redirect:/order";
    }
}
