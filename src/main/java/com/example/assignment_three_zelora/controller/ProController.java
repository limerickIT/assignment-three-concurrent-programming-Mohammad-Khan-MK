package com.example.assignment_three_zelora.controller;


import com.example.assignment_three_zelora.model.dto.ProductSearch;
import com.example.assignment_three_zelora.model.entitys.Category;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.repos.CategoryRepository;
import com.example.assignment_three_zelora.model.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProController {
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProController(ProductService productService,
                         CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    // Show search page with all products (initial load)
    @GetMapping("/products")
    public String showSearchPage(Model model) {
        ProductSearch form = new ProductSearch();
        List<Category> categories = categoryRepository.findAll();
        List<Product> products = productService.getAllProducts();

        model.addAttribute("form", form);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "products/search";
    }

    // Perform search
    @GetMapping("/products/search")
    public String searchProducts(ProductSearch form, Model model) {

        List<Category> categories = categoryRepository.findAll();
        List<Product> results = productService.searchProducts(form);

        model.addAttribute("form", form);
        model.addAttribute("categories", categories);
        model.addAttribute("products", results);

        return "products/search";
    }
    @GetMapping("/products/{id}")
    public String showProductDetails(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/details";
    }




}
