package ru.otus.finalproject.rest;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.web.context.WebApplicationContext;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.exceptions.DetailingExceptionHandler;
import ru.otus.finalproject.exceptions.ProductException;
import ru.otus.finalproject.repository.user.UserRepository;
import ru.otus.finalproject.rest.dto.ProductDto;
import ru.otus.finalproject.rest.mappers.ProductMapper;
import ru.otus.finalproject.rest.mappers.ProductMapperImpl;
import ru.otus.finalproject.security.UserDetailServiceImpl;
import ru.otus.finalproject.service.products.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@DisplayName("тест ProductControllerTest должен проверять методы ")
@WebMvcTest(ProductController.class)
@Import(ProductController.class)
@ImportAutoConfiguration(DetailingExceptionHandler.class)
@ContextConfiguration(classes = {ProductMapperImpl.class})
public class ProductControllerTest {

@Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductMapper productMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserDetailServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("успешного входа в систему под пользователем")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    @Test
    public void shouldReturn200forUser() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("успешного входа в систему под администратором")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn200forAdmin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("успешного входа в систему под менеджером")
    @WithMockUser(username = "manager", authorities = "ROLE_MANAGER")
    @Test
    public void shouldReturn200forManager() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("получения всех услуг")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnAllProducts() throws Exception {
        List<Product> expectedList = Arrays.asList(
                new Product(1,"product1","productDescription",1000),
                new Product(2,"product2","product2Description",1000));
        given(productService.getAllProducts()).willReturn(expectedList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("product-list"))
                .andExpect(model().attribute("products",equalTo(expectedList)));
    }

    @DisplayName("получения одного автора по id для редактирования")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturnOneProductForEdit() throws Exception {
        Product product = new Product(1,"product1","productDescription",1000);

        given(productService.getProductById(1)).willReturn(product);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}/edit", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("product-edit"))
                .andExpect(model().attribute("product",equalTo(product)));
    }

    @DisplayName("обновления существующего автора")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldUpdateExistingProduct() throws Exception {
        final String NEW_NAME = "newProductName";
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/product/{id}", 1).with(csrf())
                .param("fullName",NEW_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/author"));
    }

    @DisplayName("создания нового автора")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldCreateNewProduct() throws Exception {
        ProductDto productDto = new ProductDto(1,"product1","productDescription",1000);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/product").with(csrf())
                .param("product",productDto.getProductName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product"));
    }

    @DisplayName("удаления автора по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldDeleteProductById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}",1).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product"));;
    }

    @DisplayName("получения ошибки при запросе несущетсвующего автора по id")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    public void shouldReturn404ByNotExistProductId() throws Exception {
        given(productService.getProductById(90)).willThrow(new ProductException("Product with id [90] not found"));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}/edit", "90"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("/error/404"))
                .andExpect(model().attribute("error",equalTo("Product with id [90] not found")));
    }

}
