package com.bosafood.controller;

import com.bosafood.dto.ReviewDTO;
import com.bosafood.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "APIs for managing customer reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Retrieves a list of all reviews in the system")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Retrieves a specific review by its ID")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/menu-item/{menuItemId}")
    @Operation(summary = "Get reviews by menu item", description = "Retrieves all reviews for a specific menu item")
    public ResponseEntity<List<ReviewDTO>> getReviewsByMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(reviewService.getReviewsByMenuItem(menuItemId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reviews by user", description = "Retrieves all reviews submitted by a specific user")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @PostMapping
    @Operation(summary = "Create review", description = "Creates a new review for a menu item")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review", description = "Updates an existing review")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Deletes a review")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
} 