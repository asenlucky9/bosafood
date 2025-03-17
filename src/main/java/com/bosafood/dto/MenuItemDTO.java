package com.bosafood.dto;

import lombok.Data;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Data
public class MenuItemDTO {
    private Long id;

    @NotBlank(message = "Menu item name is required")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    private String imageUrl;

    private Long categoryId;
    private String categoryName;

    private Boolean isVegetarian = false;
    private Boolean isSpicy = false;
    private Boolean isAvailable = true;

    @Min(value = 0, message = "Preparation time cannot be negative")
    private Integer preparationTime;

    @Min(value = 0, message = "Calories cannot be negative")
    private Integer calories;

    private String allergens;

    private Double rating;
    private Integer reviewCount;
} 