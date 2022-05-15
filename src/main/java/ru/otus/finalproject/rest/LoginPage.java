package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPage {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}
