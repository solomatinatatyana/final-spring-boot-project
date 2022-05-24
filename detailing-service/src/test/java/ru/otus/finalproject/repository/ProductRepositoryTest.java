package ru.otus.finalproject.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.repository.products.ProductRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест класса ProductRepository должен ")
@DataJpaTest
public class ProductRepositoryTest {

    private static final long EXISTING_FIRST_PRODUCT_ID = 1L;
    private static final String EXISTING_FIRST_PRODUCT_NAME = "Восстановительная полировка кузова";
    private static final long EXISTING_SECOND_PRODUCT_ID = 2L;
    private static final String EXISTING_SECOND_PRODUCT_NAME = "Защитное покрытие на кузов авто";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("проверять добавление новой услуги в БД")
    @Test
    public void shouldInsertNewProduct() {
        Product expectedProduct = new Product("testProduct", "testProductDescription");
        productRepository.saveAndFlush(expectedProduct);
        assertThat(expectedProduct.getId()).isGreaterThan(0);
        Product actualProduct = productRepository.findById(expectedProduct.getId()).get();
        assertThat(actualProduct).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("проверять нахождение услуги по его id")
    @Test
    public void shouldReturnProductById() {
        Product expectedProduct = em.find(Product.class, EXISTING_FIRST_PRODUCT_ID);
        Product actualProduct = productRepository.findById(expectedProduct.getId()).get();
        assertThat(actualProduct).usingRecursiveComparison().isEqualTo(expectedProduct);
   }

    @DisplayName("проверять нахождение всех услуг")
    @Test
    public void shouldReturnExpectedProductList() {
        List<Product> expectedProductList = Arrays.asList(
                new Product(EXISTING_FIRST_PRODUCT_ID, EXISTING_FIRST_PRODUCT_NAME, "description",1000),
                new Product(EXISTING_SECOND_PRODUCT_ID, EXISTING_SECOND_PRODUCT_NAME,"description",1000)
        );
        List<Product> actualProductList = productRepository.findAll();
        assertThat(actualProductList).containsAll(expectedProductList);
    }

    @DisplayName("проверять удаление услуги по его id")
    @Test
    public void shouldDeleteCorrectProductById() {
        Product secondProduct = em.find(Product.class, EXISTING_SECOND_PRODUCT_ID);
        assertThat(secondProduct).isNotNull();
        em.detach(secondProduct);

        productRepository.deleteById(EXISTING_SECOND_PRODUCT_ID);
        Product deletedProduct = em.find(Product.class, EXISTING_SECOND_PRODUCT_ID);

        assertThat(deletedProduct).isNull();
    }


}
