package ru.otus.tariffservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.tariffservice.domain.Tariff;
import ru.otus.tariffservice.repository.TariffRepository;

@RestController
@RequestMapping(value = "/tariff")
public class TariffController {
    private final TariffRepository tariffRepository;

    public TariffController(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }


    @GetMapping(value = "/brand/{brand}")
    public Tariff getTariff(@PathVariable String brand) {
        return tariffRepository.findByBrand(brand);
    }
}
