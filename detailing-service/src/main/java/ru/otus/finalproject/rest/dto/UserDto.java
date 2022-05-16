package ru.otus.finalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.finalproject.domain.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private boolean active;

    private Role roles;
}
