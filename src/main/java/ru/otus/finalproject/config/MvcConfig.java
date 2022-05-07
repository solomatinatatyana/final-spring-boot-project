package ru.otus.finalproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/css/**",
                        "/bootstrap/css/**",
                        "/bootstrap/js/**",
                        "/bootstrap_theme/**",
                        "/assets/js/**")
                .addResourceLocations("classpath:/static/css/",
                        "classpath:/static/bootstrap/css/",
                        "classpath:/static/bootstrap/js/",
                        "classpath:/static/bootstrap_theme/",
                        "classpath:/static/assets/js/");
    }
}
