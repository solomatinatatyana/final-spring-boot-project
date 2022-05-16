package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.finalproject.service.cars.CarService;
import ru.otus.finalproject.service.products.ProductService;

@Controller
public class ProductCostController {

    private final ProductService productService;
    private final CarService carService;

    public ProductCostController(ProductService productService, CarService carService) {
        this.productService = productService;
        this.carService = carService;
    }

    @GetMapping(value = "/product/cost")
    public String mainPage(Model model){
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("cars",carService.getAllCars());
        return "calculate-cost-form";
    }

    @PostMapping(value = "/poduct/cost")
    public String calculateProductsCost(String brand, Model model){
        return "/";
    }
}
