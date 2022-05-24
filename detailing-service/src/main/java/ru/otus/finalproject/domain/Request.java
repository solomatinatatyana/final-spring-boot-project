package ru.otus.finalproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "requests")
@NamedEntityGraph(name = "request-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("car")
        })
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "status")
    private String status;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "car_brand_id")
    private Car car;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.DETACH)
    @JoinTable(name = "requests_products",
            joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> products;

    @Column(name = "comment")
    private String comment;

    public Request(long id, String firstName, String status, String phone, Car car, List<Product> products, String comment) {
        this.id = id;
        this.firstName = firstName;
        this.status = status;
        this.phone = phone;
        this.car = car;
        this.products = products;
        this.comment = comment;
    }
}
