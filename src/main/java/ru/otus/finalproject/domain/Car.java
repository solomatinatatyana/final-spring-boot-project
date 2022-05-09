package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@NotBlank(message = "Поле обязательное и не может быть пустым")
    @Column(name = "brand")
    private String brand;

    /*@Column(name = "model")
    private String model;*/

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Order> orders;
}
