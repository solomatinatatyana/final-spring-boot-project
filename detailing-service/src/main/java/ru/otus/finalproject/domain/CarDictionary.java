package ru.otus.finalproject.domain;

import lombok.Getter;

@Getter
public enum CarDictionary {
    SKODA_RAPID("Skoda Rapid"),
    SKODA_OCTAVIA("Skoda Octavia"),
    MAZDA_3("Mazda 3"),
    NISSAN_TEANA("Nissan Teana"),
    TAYOTA_CAMRY("Tayota Camry"),
    HONDA_CIVIC("Honda Civic"),
    AUDI_Q5("Audi Q5"),
    BMW_M2("BMW M2"),
    ASTON_MARTIN_RAPIDE("Aston Martin Rapide");

    private String displayName;

    CarDictionary(String displayName) {
        this.displayName = displayName;
    }
}
