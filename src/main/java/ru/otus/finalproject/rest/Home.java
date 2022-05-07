package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {
    @RequestMapping(value = "/")
    public String mainPage(){
        return "index";
    }
}
