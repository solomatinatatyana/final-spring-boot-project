package ru.otus.finalproject.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.finalproject.dto.ProductPriceVO;

@FeignClient(name = "calculator-costs-service", path = "/cost")
public interface ProductPriceCalculatorServiceProxy {

    @GetMapping(value = "/{product}/brand/{brand}")
    public ProductPriceVO getPrice(@PathVariable String product, @PathVariable String brand);

}
