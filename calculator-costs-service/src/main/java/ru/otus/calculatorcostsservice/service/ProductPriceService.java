package ru.otus.calculatorcostsservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.calculatorcostsservice.domain.ProductPrice;
import ru.otus.calculatorcostsservice.domain.TariffVO;
import ru.otus.calculatorcostsservice.repository.ProductPriceRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    private final RestTemplate restTemplate;

    public ProductPriceService(ProductPriceRepository productPriceRepository, RestTemplate restTemplate) {
        this.productPriceRepository = productPriceRepository;
        this.restTemplate = restTemplate;
    }

    public ProductPrice getProductPriceByProduct(String productName) {
        return productPriceRepository.findFirstByProductName(productName);
    }

    public BigDecimal getTariff(String brand) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        urlPathVariables.put("brand", brand);
        ResponseEntity<TariffVO> responseEntity = restTemplate.getForEntity(
                "http://localhost:8002/tariff/brand/{brand}", TariffVO.class, urlPathVariables);
        TariffVO converter = responseEntity.getBody();
        return converter.getTariff();
    }
}
