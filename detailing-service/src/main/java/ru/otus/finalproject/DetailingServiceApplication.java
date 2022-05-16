package ru.otus.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class DetailingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetailingServiceApplication.class, args);
    }

}
