package ru.otus.finalproject.repository.orders;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.finalproject.domain.Order;
import ru.otus.finalproject.domain.Product;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findOrderByCode(String code);

    List<Order> findOrderByStatus(String status);

    @EntityGraph(value = "order-entity-graph")
    List<Order> findAll();

    boolean existsOrderByCode(String code);

}
