package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "brand")
    private String brand;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "car")
    private List<Order> orders;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "car")
    private Set<Request> requests;

    public Car(long id, String brand) {
        this.id = id;
        this.brand = brand;
    }
}
