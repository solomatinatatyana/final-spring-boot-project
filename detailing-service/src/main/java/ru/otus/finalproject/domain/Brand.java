package ru.otus.finalproject.domain;

import lombok.Getter;

@Getter
public enum Brand {
    SKODA("Skoda"),
    MAZDA("Mazda"),
    NISSAN("Nissan"),
    TAYOTA("Tayota"),
    HONDA("Honda"),
    AUDI("Audi"),
    BMW("BMW"),
    ASTON_MARTIN("Aston Martin");

    private String displayName;

    Brand(String displayName) {
        this.displayName = displayName;
    }
}
