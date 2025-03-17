package com.bosafood.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> items = new ArrayList<>();
    private BigDecimal totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
} 