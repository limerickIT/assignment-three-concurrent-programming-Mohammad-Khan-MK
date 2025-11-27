package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.dto.ProductSearch;
import com.example.assignment_three_zelora.model.entitys.Inventory;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Review;
import com.example.assignment_three_zelora.model.repos.InventoryRepository;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import com.example.assignment_three_zelora.model.repos.ReviewRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final InventoryRepository inventoryRepository;

    public ProductService(ProductRepository productRepository,
                          ReviewRepository reviewRepository,
                          InventoryRepository inventoryRepository) {

        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.inventoryRepository = inventoryRepository;
    }



    //create
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    //Get all
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //get one
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    //Update
    public Product updateProduct(Integer id, Product updatedProduct) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        updatedProduct.setProductId(id);
        return productRepository.save(updatedProduct);
    }

    //Delete by id
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
    public List<Product> searchProducts(ProductSearch form) {

        String name = (form.getName() == null || form.getName().isBlank())
                ? null : form.getName();

        String category = (form.getCategory() == null || form.getCategory().isBlank())
                ? null : form.getCategory();

        BigDecimal minPrice = form.getMinPrice();
        BigDecimal maxPrice = form.getMaxPrice();

        String productDesc = (form.getProductDesc() == null || form.getProductDesc().isBlank())
                ? null : form.getProductDesc();

        Date releaseAfter = null;
        if (Boolean.TRUE.equals(form.getRecent())) {
            LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
            releaseAfter = java.sql.Date.valueOf(sevenDaysAgo);
        }

        return productRepository.searchProducts(
                name,
                category,
                minPrice,
                maxPrice,
                productDesc,
                releaseAfter
        );
    }
    public List<Product> getThreeCheapestProducts() {
        return productRepository.findTop3ByOrderByPriceAsc();
    }


    public List<Review> getGoodReviews(Integer productId) {
        return reviewRepository.findGoodReviews(productId);
    }

    public Double getAverageRating(Integer productId) {
        Double avg = reviewRepository.findAverageRating(productId);
        return (avg == null) ? 0.0 : avg;
    }


    // inventory
    public Inventory getInventory(Integer productId) {
        return inventoryRepository.findByProductId_ProductId(productId);
    }

    //similar products
    public List<Product> getSimilarProducts(Product product) {

        if (product == null || product.getCategoryId() == null) {
            return List.of();
        }

        return productRepository.findTop4ByCategoryIdAndProductIdNot(
                product.getCategoryId(),
                product.getProductId()
        );
    }


}