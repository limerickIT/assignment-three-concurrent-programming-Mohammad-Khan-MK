package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
    SELECT p FROM Product p JOIN p.categoryId c
    WHERE (:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:category IS NULL OR c.categoryName = :category)
    AND (:minPrice IS NULL OR p.price >= :minPrice)
    AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    AND (:productDesc IS NULL OR p.description LIKE CONCAT('%', :productDesc, '%'))
    AND (:releaseAfter IS NULL OR p.releaseDate >= :releaseAfter)
    ORDER BY p.productId ASC
    """)
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("productDesc") String productDesc,
            @Param("releaseAfter") Date releaseAfter
    );

    List<Product> findTop3ByOrderByPriceAsc();
}




