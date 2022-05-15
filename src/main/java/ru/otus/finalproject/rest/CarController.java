package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.service.cars.CarService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    //@Timed(GET_AUTHORS_REQ_TIME)
    @GetMapping(value = "/admin/car")
    public String getCars(Model model){
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "car-dictionary";
    }

    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
    @GetMapping(value = "/admin/car/{id}/edit")
    public String editCar(@PathVariable("id") long id, Model model){
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "car-dictionary-edit";
    }

    //@Timed(PATCH_AUTHOR_REQ_TIME)
    @PatchMapping(value = "/admin/car/{id}")
    public String saveCar(@PathVariable("id") long id,
                              @ModelAttribute("car") @Valid Car car, BindingResult result){
        //authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        if(result.hasErrors()){
            return "car-dictionary";
        }
        carService.updateCarById(id,car);
        return "redirect:/admin/car";
    }

    //@Timed(CREATE_AUTHOR_REQ_TIME)
    @PostMapping(value = "/admin/car")
    public String createCar(@ModelAttribute("car") @Valid Car car){
        //authorService.insertAuthor(authorMapper.toAuthor(author));
        carService.createCar(car);
        return "redirect:/admin/car";
    }

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    @DeleteMapping(value = "/admin/car/{id}")
    public String deleteCar(@PathVariable("id") long id){
        carService.deleteCarById(id);
        return "redirect:/admin/car";
    }
}
