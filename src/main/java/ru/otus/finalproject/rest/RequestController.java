package ru.otus.finalproject.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.Request;
import ru.otus.finalproject.service.cars.CarService;
import ru.otus.finalproject.service.orders.OrderService;
import ru.otus.finalproject.service.products.ProductService;
import ru.otus.finalproject.service.requests.RequestService;

import javax.validation.Valid;
import java.util.List;

import static ru.otus.finalproject.domain.RequestStatus.*;

@Slf4j
@Controller
public class RequestController {
    private final RequestService requestService;
    private final ProductService productService;
    private final CarService carService;
    private final OrderService orderService;

    public RequestController(RequestService requestService, ProductService productService, CarService carService, OrderService orderService) {
        this.requestService = requestService;
        this.productService = productService;
        this.carService = carService;
        this.orderService = orderService;
    }

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
            //requests = requestService.getAllRequests();
        }
        model.addAttribute("requests", requests);
        return "request-list";
    }

    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
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

    //@Timed(PATCH_AUTHOR_REQ_TIME)
    @PatchMapping(value = "/request/{id}")
    public String saveRequest(@PathVariable("id") long id,
                            @ModelAttribute("request") @Valid Request request, BindingResult result){
        //authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        if(result.hasErrors()){
            return "request-edit";
        }
        requestService.updateRequestById(id,request);
        return "redirect:/request";
    }

   /* @GetMapping(value = "/")
    public String request(Model model){
        model.addAttribute("cars",productService.getAllProducts());
        return "index";
    }*/


    //@Timed(CREATE_AUTHOR_REQ_TIME)
    @PostMapping(value = "/request")
    public String createRequest( @Valid @ModelAttribute("request") Request request,
                              String brand, Model model){
        //authorService.insertAuthor(authorMapper.toAuthor(author));
       /* if(result.hasErrors()){
            return "request-add";
        }*/
        Car car = carService.getCarByBrand(brand);

        requestService.createRequest(request,car);
        return "redirect:/";
    }

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    @DeleteMapping(value = "/request/{id}")
    public String deleteRequset(@PathVariable("id") long id){
        requestService.deleteRequestById(id);
        return "redirect:/request";
    }

    @PostMapping(value = "/request/{id}/cancel")
    public String cancelRequest(@PathVariable("id") long id){
        requestService.setStatusByRequestId(id, CANCELED.getRusName());
        return "redirect:/request";
    }

    @PostMapping(value = "/request/{id}/approve")
    public String approveRequest(@PathVariable("id") long id){
        requestService.setStatusByRequestId(id, APPROVED.getRusName());
        Request request = requestService.getRequestById(id);
        orderService.createOrderFromRequest(request);
        return "redirect:/order";
    }
}
