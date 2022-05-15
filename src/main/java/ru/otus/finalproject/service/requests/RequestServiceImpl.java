package ru.otus.finalproject.service.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.Request;
import ru.otus.finalproject.domain.RequestStatus;
import ru.otus.finalproject.exceptions.RequestException;
import ru.otus.finalproject.repository.cars.CarRepository;
import ru.otus.finalproject.repository.products.ProductRepository;
import ru.otus.finalproject.repository.requests.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final ProductRepository productRepository;
    private final CarRepository carRepository;

    public RequestServiceImpl(RequestRepository requestRepository, ProductRepository productRepository, CarRepository carRepository) {
        this.requestRepository = requestRepository;
        this.productRepository = productRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    @Override
    public void createRequest(Request request, Car car) {
        request.setFirstName(request.getFirstName());
        request.setPhone(request.getPhone());
        request.setCar(car);
        request.setStatus(RequestStatus.NEW.getRusName());
        request.setComment(request.getComment());
        requestRepository.saveAndFlush(request);
    }

    @Transactional
    @Override
    public void updateRequestById(long id, Request request) {
        Request requestToBeUpdated = getRequestById(id);
        requestToBeUpdated.setStatus(request.getStatus());
        requestToBeUpdated.setFirstName(request.getFirstName());
        requestToBeUpdated.setPhone(request.getPhone());
        requestToBeUpdated.setCar(carRepository.findByBrand(request.getCar().getBrand()).get());

        Iterable<Long> productsIds = request.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        requestToBeUpdated.setProducts(productRepository.findAllById(productsIds));

        requestRepository.saveAndFlush(requestToBeUpdated);
    }

    @Override
    public Request getRequestById(long id) {
        return requestRepository.findById(id).orElseThrow(()->new RequestException("request with id [" + id + "] not found"));
    }

    @Override
    public List<Request> getAllRequestsByStatus(String status) {
        return requestRepository.findRequestByStatus(status);
    }

    @Override
    public List<Request> getAllRequestsByPhone(String phone) {
        return null;
    }

    @Override
    public List<Request> getAllRequestsByCustomerName(String customerName) {
        return null;
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteRequestById(long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public void setStatusByRequestId(long id, String status) {
        Request request = this.getRequestById(id);
        request.setStatus(status);
        this.updateRequestById(id,request);
    }
}
