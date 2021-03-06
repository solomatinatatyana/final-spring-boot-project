package ru.otus.finalproject.rest;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.Request;
import ru.otus.finalproject.rest.dto.RequestDto;
import ru.otus.finalproject.rest.mappers.CarMapper;
import ru.otus.finalproject.rest.mappers.OrderMapper;
import ru.otus.finalproject.rest.mappers.ProductMapper;
import ru.otus.finalproject.rest.mappers.RequestMapper;
import ru.otus.finalproject.service.cars.CarService;
import ru.otus.finalproject.service.orders.OrderService;
import ru.otus.finalproject.service.products.ProductService;
import ru.otus.finalproject.service.requests.RequestService;

import javax.validation.Valid;
import java.util.List;

import static ru.otus.finalproject.domain.RequestStatus.*;
import static ru.otus.finalproject.metrics.Metrics.Requests.*;

@Slf4j
@Controller
public class RequestController {
    private final RequestService requestService;
    private final ProductService productService;
    private final CarService carService;
    private final OrderService orderService;
    private final RequestMapper requestMapper;
    private final ProductMapper productMapper;
    private final CarMapper carMapper;
    private final OrderMapper orderMapper;

    public RequestController(RequestService requestService, ProductService productService, CarService carService, OrderService orderService,
                             RequestMapper requestMapper, ProductMapper productMapper, CarMapper carMapper, OrderMapper orderMapper) {
        this.requestService = requestService;
        this.productService = productService;
        this.carService = carService;
        this.orderService = orderService;
        this.requestMapper = requestMapper;
        this.productMapper = productMapper;
        this.carMapper = carMapper;
        this.orderMapper = orderMapper;
    }

    @Timed(GET_REQUESTS_REQ_TIME)
    @GetMapping(value = "/request")
    public String getRequests(
            @RequestParam(required = false, name = "fio") String firstName,
            @RequestParam(required = false, name = "status") String status,
            @RequestParam(required = false, name = "phone") String phone,
            Model model){
        List<Request> requests;
        if(firstName!=null && !firstName.isEmpty()){
            requests = requestService.getAllRequestsByCustomerName(firstName);
        }else if(status!=null && !status.isEmpty()){
            requests = requestService.getAllRequestsByStatus(status);
        }else if(phone!=null && !phone.isEmpty()) {
            requests = requestService.getAllRequestsByPhone(phone);
        }else{
            requests = requestService.getAllRequestsByStatus(NEW.getRusName());
        }
        model.addAttribute("requests", requests);
        return "request-list";
    }

    @Timed(GET_REQUEST_EDIT_REQ_TIME)
    @GetMapping(value = "/request/{id}/edit")
    public String editRequest(@PathVariable("id") long id, Model model){
        Request request = requestService.getRequestById(id);
        List<Product> productList = productService.getAllProducts();
        List<Car> carList = carService.getAllCars();
        model.addAttribute("request", request);
        model.addAttribute("cars", carList);
        model.addAttribute("products",productList);
        return "request-edit";
    }

    @Timed(PATCH_REQUEST_REQ_TIME)
    @PatchMapping(value = "/request/{id}")
    public String saveRequest(@PathVariable("id") long id,
                            @ModelAttribute("request") @Valid Request request, BindingResult result){
        if(result.hasErrors()){
            return "request-edit";
        }
        requestService.updateRequestById(id,request);
        return "redirect:/request";
    }


    @Timed(CREATE_REQUEST_REQ_TIME)
    @PostMapping(value = "/request")
    public String createRequest( @Valid @ModelAttribute("request") RequestDto request,
                              String brand, Model model){
        Car car = carService.getCarByBrand(brand);

        requestService.createRequest(requestMapper.toRequest(request), car);
        return "redirect:/";
    }

    @Timed(DELETE_REQUEST_REQ_TIME)
    @DeleteMapping(value = "/request/{id}")
    public String deleteRequest(@PathVariable("id") long id){
        requestService.deleteRequestById(id);
        return "redirect:/request";
    }

    @Timed(CANCEL_REQUEST_REQ_TIME)
    @PostMapping(value = "/request/{id}/cancel")
    public String cancelRequest(@PathVariable("id") long id){
        requestService.setStatusByRequestId(id, CANCELED.getRusName());
        return "redirect:/request";
    }

    @Timed(APPROVE_REQUEST_REQ_TIME)
    @PostMapping(value = "/request/{id}/approve")
    public String approveRequest(@PathVariable("id") long id){
        requestService.setStatusByRequestId(id, APPROVED.getRusName());
        Request request = requestService.getRequestById(id);
        orderService.createOrderFromRequest(request);
        return "redirect:/order";
    }
}
