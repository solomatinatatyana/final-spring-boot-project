package ru.otus.finalproject.service.products;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.exceptions.ProductException;
import ru.otus.finalproject.repository.products.ProductRepository;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public void createProduct(Product product) {
        if(!productRepository.existsProductByProductName(product.getProductName())){
            productRepository.saveAndFlush(product);
        }else {
            throw new ProductException("product with name ["+ product.getProductName() +"] is already exist!");
        }
    }

    @Transactional
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

    @Transactional
    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
