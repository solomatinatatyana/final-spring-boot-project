package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@NamedEntityGraph(name = "order-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("car"),
                @NamedAttributeNode("user")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private String code;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "car_brand_id")
    private Car car;

    @Column(name = "status")
    private String status;

    @Column(name = "total")
    private Double total;

    /*@Column(name = "request_id")
    private long requestId;*/

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.DETACH)
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> products;

}
