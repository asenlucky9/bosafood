package com.bosafood.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentTransactionDTO {
    private Long transactionId;
    private Long orderId;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private String transactionReference;
    private LocalDateTime createdAt;
} 