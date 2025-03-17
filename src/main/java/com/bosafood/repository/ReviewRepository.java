package com.bosafood.repository;

import com.bosafood.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.menuItem")
    List<Review> findAllWithDetails();
    
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.menuItem WHERE r.id = :id")
    Optional<Review> findByIdWithDetails(@Param("id") Long id);
    
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.menuItem WHERE r.menuItem.id = :itemId")
    List<Review> findByMenuItem_Id(@Param("itemId") Long itemId);
    
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.menuItem WHERE r.user.id = :userId")
    List<Review> findByUser_Id(@Param("userId") Long userId);
    
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.menuItem WHERE r.menuItem.id = :itemId AND r.rating = :rating")
    List<Review> findByMenuItem_IdAndRating(@Param("itemId") Long itemId, @Param("rating") Integer rating);
} 