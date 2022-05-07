package ru.otus.finalproject.service.products;

import org.springframework.stereotype.Service;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.exceptions.ProductException;
import ru.otus.finalproject.repository.products.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(Product product) {
        productRepository.saveAndFlush(product);
    }

    @Override
    public void updateProductById(long id, Product product) {
        Product productToBeUpdated = getProductById(id);
        productToBeUpdated.setProductName(product.getProductName());
        productToBeUpdated.setDescription(product.getDescription());
        productToBeUpdated.setPrice(product.getPrice());
        productRepository.saveAndFlush(productToBeUpdated);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductException("product with id [" + id + "] not found"));
    }

    @Override
    public Product getProductByName(String productName) {
        return productRepository.findByProductName(productName).orElseThrow(()->new ProductException("Product with name [" + productName + "] not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
