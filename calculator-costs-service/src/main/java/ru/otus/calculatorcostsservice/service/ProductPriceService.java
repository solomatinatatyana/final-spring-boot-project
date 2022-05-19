package ru.otus.calculatorcostsservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.calculatorcostsservice.domain.ProductPrice;
import ru.otus.calculatorcostsservice.domain.TariffVO;
import ru.otus.calculatorcostsservice.feign.TariffBrandServiceProxy;
import ru.otus.calculatorcostsservice.repository.ProductPriceRepository;

import java.math.BigDecimal;

@Slf4j
@Service
public class ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    private final TariffBrandServiceProxy feignProxy;

    public ProductPriceService(ProductPriceRepository productPriceRepository, TariffBrandServiceProxy feignProxy) {
        this.productPriceRepository = productPriceRepository;
        this.feignProxy = feignProxy;
    }

    public ProductPrice getProductPriceByProduct(String productName) {
        return productPriceRepository.findFirstByProductName(productName);
    }

    public BigDecimal getTariff(String brand) {
        TariffVO tariff = feignProxy.getTariff(brand);
        return tariff.getTariff();
    }
}
