package ru.otus.finalproject.rest;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.rest.dto.ProductDto;
import ru.otus.finalproject.rest.mappers.ProductMapper;
import ru.otus.finalproject.service.products.ProductService;

import javax.validation.Valid;
import java.util.List;

import static ru.otus.finalproject.metrics.Metrics.Products.*;

@Slf4j
@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Timed(GET_PRODUCTS_REQ_TIME)
    @GetMapping(value = "/product")
    public String getProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @Timed(GET_PRODUCT_EDIT_REQ_TIME)
    @GetMapping(value = "/product/{id}/edit")
    public String editProduct(@PathVariable("id") long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @Timed(PATCH_PRODUCT_REQ_TIME)
    @PatchMapping(value = "/product/{id}")
    public String saveProduct(@PathVariable("id") long id,
                              @ModelAttribute("product") @Valid ProductDto productDto, BindingResult result){
        if(result.hasErrors()){
            return "product-edit";
        }
        productService.updateProductById(id, productMapper.toProduct(productDto));
        return "redirect:/product";
    }

    @Timed(GET_PRODUCT_ADD_REQ_TIME)
    @GetMapping(value = "/product/add")
    public String showAddProductForm(Model model){
        model.addAttribute("product",new Product());
        return "product-add";
    }

    @Timed(CREATE_PRODUCT_REQ_TIME)
    @PostMapping(value = "/product")
    public String createProduct(@ModelAttribute("product") @Valid ProductDto productDto, BindingResult result){
        if(result.hasErrors()){
            return "product-add";
        }
        productService.createProduct(productMapper.toProduct(productDto));
        return "redirect:/product";
    }

    @Timed(DELETE_PRODUCT_REQ_TIME)
    @DeleteMapping(value = "/product/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteProductById(id);
        return "redirect:/product";
    }


}
