package com.bosafood.service;

import com.bosafood.dto.ReviewDTO;
import com.bosafood.mapper.ReviewMapper;
import com.bosafood.model.MenuItem;
import com.bosafood.model.Review;
import com.bosafood.model.User;
import com.bosafood.repository.MenuItemRepository;
import com.bosafood.repository.ReviewRepository;
import com.bosafood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                        UserRepository userRepository,
                        MenuItemRepository menuItemRepository,
                        ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        MenuItem menuItem = menuItemRepository.findById(reviewDTO.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setUser(user);
        review.setMenuItem(menuItem);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toDTO(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByMenuItem(Long menuItemId) {
        return reviewRepository.findByMenuItem_Id(menuItemId).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByUser(Long userId) {
        return reviewRepository.findByUser_Id(userId).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewMapper.updateEntity(reviewDTO, review);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public double getAverageRatingForMenuItem(Long menuItemId) {
        return reviewRepository.findByMenuItem_Id(menuItemId).stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}