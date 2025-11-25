package com.example.assignment_three_zelora.model.dto;

import java.math.BigDecimal;

public class ProductSearch {

    private String name;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean recent;
    private String productDesc;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }

    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }

    public Boolean getRecent() { return recent; }
    public void setRecent(Boolean recent) { this.recent = recent; }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
