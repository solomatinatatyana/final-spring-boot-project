package ru.otus.finalproject.rest;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.rest.dto.CarDto;
import ru.otus.finalproject.rest.mappers.CarMapper;
import ru.otus.finalproject.service.cars.CarService;

import javax.validation.Valid;
import java.util.List;

import static ru.otus.finalproject.metrics.Metrics.Cars.*;

@Controller
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @Timed(GET_CARS_REQ_TIME)
    @GetMapping(value = "/admin/car" )
    public String getCars(Model model){
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "car-dictionary";
    }

    @Timed(GET_CAR_EDIT_REQ_TIME)
    @GetMapping(value = "/admin/car/{id}/edit")
    public String editCar(@PathVariable("id") long id, Model model){
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "car-dictionary-edit";
    }

    @Timed(PATCH_CAR_REQ_TIME)
    @PatchMapping(value = "/admin/car/{id}")
    public String saveCar(@PathVariable("id") long id,
                          @ModelAttribute("car") @Valid CarDto carDto, BindingResult result){
        if(result.hasErrors()){
            return "car-dictionary";
        }
        carService.updateCarById(id, carMapper.toCar(carDto));
        return "redirect:/admin/car";
    }

    @Timed(CREATE_CAR_REQ_TIME)
    @PostMapping(value = "/admin/car")
    public String createCar(@ModelAttribute("car") @Valid CarDto carDto){
        carService.createCar(carMapper.toCar(carDto));
        return "redirect:/admin/car";
    }

    @Timed(DELETE_CAR_REQ_TIME)
    @DeleteMapping(value = "/admin/car/{id}")
    public String deleteCar(@PathVariable("id") long id){
        carService.deleteCarById(id);
        return "redirect:/admin/car";
    }
}
