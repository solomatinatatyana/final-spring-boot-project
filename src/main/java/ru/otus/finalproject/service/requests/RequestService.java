package ru.otus.finalproject.service.requests;

import ru.otus.finalproject.domain.Car;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Request;

import java.util.List;

public interface RequestService {
    void createRequest(Request request, Car car);
    void updateRequestById(long id, Request request);
    Request getRequestById(long id);
    List<Request> getAllRequestsByStatus(String status);
    List<Request> getAllRequestsByPhone(String phone);
    List<Request> getAllRequestsByCustomerName(String customerName);

    List<Request> getAllRequests();
    void deleteRequestById(long id);

    void setStatusByRequestId(long id, String status);
}
