package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    //rating above 3
    @Query("SELECT r FROM Review r WHERE r.productId.productId = :productId AND r.rating >= 3")
    List<Review> findGoodReviews(int productId);

    // Average rating
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId.productId = :productId")
    Double findAverageRating(int productId);
}
