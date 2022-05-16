package ru.otus.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatus {
    NEW("Новая"),
    CANCELED("Отменена"),
    APPROVED("Подтверждена");

    private String rusName;
}
