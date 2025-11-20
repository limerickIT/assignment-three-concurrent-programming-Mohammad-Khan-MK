package com.example.assignment_three_zelora.controller;


import com.example.assignment_three_zelora.model.dto.ProductSearch;
import com.example.assignment_three_zelora.model.entitys.Category;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.repos.CategoryRepository;
import com.example.assignment_three_zelora.model.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        ProductSearch form = new ProductSearch();
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("form", form);
        model.addAttribute("categories", categories);
        model.addAttribute("products", productService.getAllProducts());

        return "products/search";


    }
    @GetMapping("/products/search")
    public String searchProducts(ProductSearch form, Model model) {

        System.out.println("Search Name: " + form.getName());
        System.out.println("Search Category: " + form.getCategory());

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("form", form);
        model.addAttribute("categories", categories);
        model.addAttribute("products", productService.getAllProducts());

        return "products/search";
    }

}

