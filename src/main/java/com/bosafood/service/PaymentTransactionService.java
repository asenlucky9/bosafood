package com.bosafood.service;

import com.bosafood.dto.PaymentTransactionDTO;
import com.bosafood.model.PaymentTransaction;
import com.bosafood.model.Order;
import com.bosafood.repository.PaymentTransactionRepository;
import com.bosafood.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentTransactionService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<PaymentTransaction> getAllTransactions() {
        return paymentTransactionRepository.findAllWithDetails();
    }

    public PaymentTransaction getTransactionById(Long id) {
        return paymentTransactionRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new RuntimeException("Payment transaction not found"));
    }

    public List<PaymentTransaction> getTransactionsByOrderId(Long orderId) {
        return paymentTransactionRepository.findByOrder_OrderId(orderId);
    }

    public List<PaymentTransaction> getTransactionsByStatus(String status) {
        return paymentTransactionRepository.findByStatus(status);
    }

    public List<PaymentTransaction> getTransactionsByUserId(Long userId) {
        return paymentTransactionRepository.findByOrder_User_Id(userId);
    }

    @Transactional
    public PaymentTransaction createTransaction(PaymentTransactionDTO transactionDTO) {
        Order order = orderRepository.findById(transactionDTO.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found"));

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrder(order);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setTransactionReference(transactionDTO.getTransactionReference());

        return paymentTransactionRepository.save(transaction);
    }

    @Transactional
    public PaymentTransaction updateTransactionStatus(Long id, String status) {
        PaymentTransaction transaction = getTransactionById(id);
        transaction.setStatus(status);
        return paymentTransactionRepository.save(transaction);
    }
} 