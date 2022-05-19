package ru.otus.calculatorcostsservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.calculatorcostsservice.domain.TariffVO;

@FeignClient(name = "tariff-service", path = "/tariff")
public interface TariffBrandServiceProxy {
    @GetMapping(value = "/brand/{brand}")
    public TariffVO getTariff(@PathVariable String brand);
}
