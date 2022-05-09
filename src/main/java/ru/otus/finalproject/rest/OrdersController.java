package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.service.cars.CarService;
import ru.otus.finalproject.service.orders.OrderService;
import ru.otus.finalproject.service.products.ProductService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class OrdersController {

    private final OrderService orderService;
    private final ProductService productService;
    private final CarService carService;

    public OrdersController(OrderService orderService, ProductService productService, CarService carService) {
        this.orderService = orderService;
        this.productService = productService;
        this.carService = carService;
    }

    @GetMapping(value = "/order")
    public String getProducts(
            @RequestParam(required = false, name = "code") String code,
            @RequestParam(required = false, name = "brand") String brand,
            @RequestParam(required = false, name = "status") String status,
                              Model model){
        List<Order> orders;
        if(code!=null && !code.isEmpty()){
            orders = Collections.singletonList(orderService.getOrderByCode(code));
        }else if(brand!=null && !brand.isEmpty()){
            orders = orderService.getAllOrdersByCarBrand(brand);
        } else if(status!=null && !status.isEmpty()){
        orders = orderService.getAllOrdersByStatus(status);
        }else {
           /* orders = Collections.emptyList();*/
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "order-list";
    }

    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
    @GetMapping(value = "/order/{id}/edit")
    public String editAuthor(@PathVariable("id") long id, Model model){
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
    public String saveProduct(@PathVariable("id") long id,
                              @ModelAttribute("product") @Valid Order order, BindingResult result){
        //authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        if(result.hasErrors()){
            return "order-edit";
        }
        orderService.updateOrderById(id,order);
        return "redirect:/order";
    }

    @GetMapping(value = "/order/add")
    public String showAddProductForm(Model model){
        model.addAttribute("product",new Product());
        return "order-add";
    }

    //@Timed(CREATE_AUTHOR_REQ_TIME)
    @PostMapping(value = "/order")
    public String createProduct(@ModelAttribute("product") @Valid Order order, BindingResult result){
        //authorService.insertAuthor(authorMapper.toAuthor(author));
        if(result.hasErrors()){
            return "order-add";
        }
        orderService.createOrder(order);
        return "redirect:/order";
    }

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    @DeleteMapping(value = "/order/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        orderService.deleteOrderById(id);
        return "redirect:/order";
    }
}
