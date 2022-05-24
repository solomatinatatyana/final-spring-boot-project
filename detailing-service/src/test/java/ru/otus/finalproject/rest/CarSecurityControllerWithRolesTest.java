package ru.otus.finalproject.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.otus.finalproject.exceptions.DetailingExceptionHandler;
import ru.otus.finalproject.rest.mappers.CarMapper;
import ru.otus.finalproject.rest.mappers.CarMapperImpl;
import ru.otus.finalproject.security.SecurityConfig;
import ru.otus.finalproject.security.UserDetailServiceImpl;
import ru.otus.finalproject.service.cars.CarService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("тест ProductControllerWithRolesTest должен проверять методы ")
@WebMvcTest(CarController.class)
@Import(CarController.class)
@ImportAutoConfiguration(DetailingExceptionHandler.class)
@ContextConfiguration(classes = {CarMapperImpl.class, SecurityConfig.class})
public class CarSecurityControllerWithRolesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarMapper carMapper;

    @MockBean
    private CarService service;

    @MockBean
    private UserDetailServiceImpl userDetailService;


    @DisplayName("получения 403 ошибки при запросе страницы с неверной ролью")
    @WithMockUser(roles = "USER")
    @Test
    public void shouldReturn403Forbidden() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/car"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("получения всех авторов с верной ролью")
    @WithMockUser(roles = "ADMIN")
    @Test
    public void shouldReturn200Success() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/car"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
