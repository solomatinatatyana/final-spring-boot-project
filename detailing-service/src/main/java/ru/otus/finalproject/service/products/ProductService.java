package ru.otus.finalproject.service.products;

import ru.otus.finalproject.domain.Product;

import java.util.List;

public interface ProductService {
    void createProduct(Product product);
    void updateProductById(long id, Product product);
    Product getProductById(long id);
    Product getProductByName(String productName);
    List<Product> getAllProducts();
    void deleteProductById(long id);
}
