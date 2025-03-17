package com.bosafood.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class SpecialOfferDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount percentage must be less than or equal to 100")
    private BigDecimal discountPercentage;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private OffsetDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private OffsetDateTime endDate;

    private boolean active = true;

    @NotEmpty(message = "At least one menu item must be selected")
    private Set<Long> menuItemIds;

    private String termsAndConditions;

    private Integer minimumOrderValue;

    private Integer maximumDiscount;

    public Set<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(Set<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}