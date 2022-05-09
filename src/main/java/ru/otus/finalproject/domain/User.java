package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detailing_users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;

    //@ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    //@CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username"))
   /* @Enumerated(EnumType.STRING)
    private Set<Role> roles;*/
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Role roles;

    public User(long id, String username, String password, String email, boolean active, Role roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;
}
