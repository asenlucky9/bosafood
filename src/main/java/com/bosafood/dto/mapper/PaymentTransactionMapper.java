package com.bosafood.dto.mapper;

import com.bosafood.dto.PaymentTransactionDTO;
import com.bosafood.model.PaymentTransaction;
import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionMapper {
    
    public PaymentTransactionDTO toDto(PaymentTransaction transaction) {
        if (transaction == null) {
            return null;
        }

        PaymentTransactionDTO dto = new PaymentTransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setOrderId(transaction.getOrder().getOrderId());
        dto.setAmount(transaction.getAmount());
        dto.setPaymentMethod(transaction.getPaymentMethod());
        dto.setStatus(transaction.getStatus());
        dto.setTransactionReference(transaction.getTransactionReference());
        dto.setCreatedAt(transaction.getCreatedAt());
        
        if (transaction.getOrder() != null && transaction.getOrder().getUser() != null) {
            dto.setUserId(transaction.getOrder().getUser().getId());
            dto.setCustomerName(transaction.getOrder().getCustomerName());
            dto.setCustomerEmail(transaction.getOrder().getCustomerEmail());
        }
        
        return dto;
    }
} 