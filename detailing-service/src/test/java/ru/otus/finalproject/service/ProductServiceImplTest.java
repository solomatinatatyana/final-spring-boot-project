package ru.otus.finalproject.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.exceptions.ProductException;
import ru.otus.finalproject.repository.products.ProductRepository;
import ru.otus.finalproject.service.products.ProductServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис ProductService должен ")
@SpringBootTest
public class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;


    @DisplayName("получать услугу по её id")
    @Test
    public void shouldReturnProductById(){
        given(productRepository.findById(1L)).willReturn(Optional.of(new Product("testProduct","testDescription")));
        Product actualProduct = productService.getProductById(1);
        assertThat(actualProduct).isNotNull();
    }

    @DisplayName("получать все услуги")
    @Test
    public void shouldReturnAllProducts(){
        List<Product> expectedProductList = Arrays.asList(
                new Product("testProduct1","testProductDescription1"),
                new Product("testProduct2","testProductDescription2"));
        given(productRepository.findAll()).willReturn(expectedProductList);
        List<Product> actualProductList = productService.getAllProducts();
        assertThat(actualProductList.equals(expectedProductList));
    }

    @DisplayName("получать ошибку при запросе на несуществующей услуги")
    @Test
    public void shouldThrowProductException(){
        Throwable exception = assertThrows(ProductException.class,()->{
            given(productRepository.findById(2L)).willReturn(Optional.empty());
            productService.getProductById(2);
        });
        assertEquals("Product with id [2] not found",exception.getMessage());
    }
}
