package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.service.products.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //@Timed(GET_AUTHORS_REQ_TIME)
    @GetMapping(value = "/product")
    public String getProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
    @GetMapping(value = "/product/{id}/edit")
    public String editAuthor(@PathVariable("id") long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    //@Timed(PATCH_AUTHOR_REQ_TIME)
    @PatchMapping(value = "/product/{id}")
    public String saveProduct(@PathVariable("id") long id,
                             @ModelAttribute("product") @Valid Product product, BindingResult result){
        //authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        if(result.hasErrors()){
            return "product-edit";
        }
        productService.updateProductById(id,product);
        return "redirect:/product";
    }

    @GetMapping(value = "/product/add")
    public String showAddProductForm(Model model){
        model.addAttribute("product",new Product());
        return "product-add";
    }

    //@Timed(CREATE_AUTHOR_REQ_TIME)
    @PostMapping(value = "/product")
    public String createProduct(@ModelAttribute("product") @Valid Product product, BindingResult result){
        //authorService.insertAuthor(authorMapper.toAuthor(author));
        if(result.hasErrors()){
            return "product-add";
        }
        productService.createProduct(product);
        return "redirect:/product";
    }

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    @DeleteMapping(value = "/product/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteProductById(id);
        return "redirect:/product";
    }


}
