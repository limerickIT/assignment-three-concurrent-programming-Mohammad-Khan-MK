package com.example.assignment_three_zelora.controller;


import com.example.assignment_three_zelora.model.repos.CategoryRepository;
import com.example.assignment_three_zelora.model.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {

        model.addAttribute("products", productService.getAllProducts());
        return "products/search"; // simple page
    }
}

