package ru.otus.finalproject.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.Product;
import ru.otus.finalproject.service.products.ProductService;

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

    @GetMapping(value = "/product/{id}")
    public String getProductCard(@PathVariable("id") long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-card-info";
    }

    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
   /* @GetMapping(value = "/product/{id}/edit")
    public String editProduct(@PathVariable("id") long id, Model model){
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "product-edit";
    }*/

    //@Timed(PATCH_AUTHOR_REQ_TIME)
    /*@PatchMapping(value = "/product/{id}")
    public String saveProduct(@PathVariable("id") long id,
                             @ModelAttribute("author") AuthorDto authorDto){
        authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        return "redirect:/product";
    }*/

    //@Timed(CREATE_AUTHOR_REQ_TIME)
   /* @PostMapping(value = "/product")
    public String addAuthor(@ModelAttribute("author") AuthorDto author){
        authorService.insertAuthor(authorMapper.toAuthor(author));
        return "redirect:/product";
    }*/

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    /*@DeleteMapping(value = "/product/{id}")
    public String deleteAuthor(@PathVariable("id") long id){
        authorService.deleteAuthorById(id);
        return "redirect:/product";
    }*/


}
