package ru.otus.finalproject.rest;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.dto.ProductCost;
import ru.otus.finalproject.service.cars.CarService;
import ru.otus.finalproject.service.orders.OrderService;
import ru.otus.finalproject.service.products.ProductService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.finalproject.metrics.Metrics.ProductCost.CALCULATE_PRODUCT_COST_REQ_TIME;
import static ru.otus.finalproject.metrics.Metrics.ProductCost.GET_PRODUCT_COST_REQ_TIME;

@Controller
public class ProductCostController {

    private final ProductService productService;
    private final CarService carService;
    private final OrderService orderService;

    public ProductCostController(ProductService productService, CarService carService, OrderService orderService) {
        this.productService = productService;
        this.carService = carService;
        this.orderService = orderService;
    }

    @Timed(GET_PRODUCT_COST_REQ_TIME)
    @GetMapping(value = "/product/cost")
    public String productCostPage(Model model){
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("cars",carService.getAllCars());
        return "calculate-cost-form";
    }

    @Timed(CALCULATE_PRODUCT_COST_REQ_TIME)
    @PostMapping(value = "/product/cost")
    public String calculateProductsCost(@ModelAttribute("productCost") ProductCost productCost,
                                        Model model){

        List<Product> productList = productService.getAllProducts();
        List<Car> carList = carService.getAllCars();

        List<Product> products = productCost.getProducts();
        List<String> productsNameList = products.stream().map(Product::getProductName).collect(Collectors.toList());
        String brand = productCost.getBrand();

        double cost = orderService.getTotalSumWithTariff(products,brand);
        productCost.setCost(cost);

        model.addAttribute("products",productList);
        model.addAttribute("cars",carList);
        model.addAttribute("brand", brand);
        model.addAttribute("checkedProducts", productsNameList);

        model.addAttribute("cost", cost);

        return "calculate-cost-form";
    }
}
