package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    NEW("Новый"),
    ACTIVE("Активный"),
    DONE("Завершен"),
    ARCHIVE("В архиве");

    private String rusName;
}
