package com.bosafood.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String deliveryAddress;
    private String deliveryMethod;
    private String paymentMethod;
    private String orderStatus;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private String specialInstructions;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}