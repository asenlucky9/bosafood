package com.bosafood.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ReviewDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;
    private String userName;
    private String userEmail;

    @NotNull(message = "Menu item ID is required")
    private Long menuItemId;
    private String menuItemName;
    private String menuItemCategory;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @NotBlank(message = "Comment is required")
    @Size(min = 10, max = 500, message = "Comment must be between 10 and 500 characters")
    private String comment;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
} 