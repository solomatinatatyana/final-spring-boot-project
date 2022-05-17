package ru.otus.calculatorcostsservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.calculatorcostsservice.domain.ProductPrice;
import ru.otus.calculatorcostsservice.service.ProductPriceService;

import java.math.BigDecimal;
import java.math.MathContext;

@RestController
@RequestMapping(value = "/cost/{product}/tariff/{brand}")
public class CalculatorCostController {
    private final ProductPriceService productPriceService;

    public CalculatorCostController(ProductPriceService productPriceService) {
        this.productPriceService = productPriceService;
    }

    @GetMapping
    public ProductPrice getProductCost(@PathVariable String product, @PathVariable String brand) {
        ProductPrice cost = productPriceService.getProductPriceByProduct(product);
        cost.setBrand(brand);
        BigDecimal tariff = productPriceService.getTariff(brand);
        BigDecimal finalCost = cost.getPrice().add(tariff, MathContext.UNLIMITED);
        cost.setPrice(finalCost);
        return cost;
    }
}
