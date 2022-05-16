package ru.otus.finalproject.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DetailingExceptionHandler {

    @ExceptionHandler(CarException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") CarException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(OrderException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") OrderException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(RequestException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") RequestException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(ProductException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") ProductException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") UsernameNotFoundException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    /*@ExceptionHandler(UserException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") UserException ex){
        return new ModelAndView("/error/500","error",ex.getMessage());
    }*/

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView accessError(@ModelAttribute("ex") AccessDeniedException ex){
        return new ModelAndView("/error/403","error",ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView internalError(@ModelAttribute("ex") Exception ex){
        return new ModelAndView("/error/500","error",ex.getMessage());
    }
}
