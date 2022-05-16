package ru.otus.finalproject.repository.requests;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.domain.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findRequestByStatus(String status);

    @EntityGraph(value = "request-entity-graph")
    List<Request> findAll();
}
