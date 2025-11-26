package com.example.assignment_three_zelora.controller;


import com.example.assignment_three_zelora.model.dto.ProductSearch;
import com.example.assignment_three_zelora.model.entitys.Category;
import com.example.assignment_three_zelora.model.entitys.Inventory;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Review;
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
        if (product == null) {
            return "redirect:/products";
        }

        List<Review> reviews = productService.getGoodReviews(id);
        Double avgRating = productService.getAverageRating(id);

        Inventory inventory = productService.getInventory(id);

        int available = 0;
        String stockMessage = "Out of stock";

        if (inventory != null) {
            available = inventory.getQuantityInStock() - inventory.getQuantityReserved();

            if (available <= 0) {
                stockMessage = "Out of stock";
            } else if (available <= inventory.getReorderPoint()) {
                stockMessage = "Low stock — only " + available + " left!";
            } else {
                stockMessage = "In Stock (" + available + ")";
            }
        }

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);

        model.addAttribute("stockMessage", stockMessage);
        model.addAttribute("available", available);

        return "products/details";
    }




    @GetMapping("/")
    public String showHome(Model model) {
        List<Product> featured = productService.getThreeCheapestProducts();
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("featuredProducts", featured);

        model.addAttribute("categories", categories);

        return "home";
    }






}
