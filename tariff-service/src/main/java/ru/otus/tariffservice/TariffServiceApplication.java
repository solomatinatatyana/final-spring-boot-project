package ru.otus.tariffservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TariffServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TariffServiceApplication.class, args);
    }

}
