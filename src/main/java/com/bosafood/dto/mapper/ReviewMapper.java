package com.bosafood.dto.mapper;

import com.bosafood.dto.ReviewDTO;
import com.bosafood.model.Review;
import com.bosafood.model.User;
import com.bosafood.model.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    
    public ReviewDTO toDto(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());
        
        User user = review.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setUserName(user.getFirstName() + " " + user.getLastName());
            dto.setUserEmail(user.getEmail());
        }
        
        MenuItem menuItem = review.getMenuItem();
        if (menuItem != null) {
            dto.setMenuItemId(menuItem.getId());
            dto.setMenuItemName(menuItem.getName());
            if (menuItem.getCategory() != null) {
                dto.setMenuItemCategory(menuItem.getCategory().getName());
            }
        }
        
        return dto;
    }
} 