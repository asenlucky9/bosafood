package com.bosafood.controller;

import com.bosafood.dto.PaymentTransactionDTO;
import com.bosafood.model.PaymentTransaction;
import com.bosafood.service.PaymentTransactionService;
import com.bosafood.dto.mapper.PaymentTransactionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment-transactions")
@CrossOrigin(origins = "*")
@Tag(name = "Payment Transaction Management", description = "APIs for managing payment transactions")
public class PaymentTransactionController {

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Operation(summary = "Get all transactions", description = "Retrieves a list of all payment transactions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PaymentTransactionDTO>> getAllTransactions() {
        List<PaymentTransaction> transactions = paymentTransactionService.getAllTransactions();
        List<PaymentTransactionDTO> dtos = transactions.stream()
            .map(paymentTransactionMapper::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get transaction by ID", description = "Retrieves a specific payment transaction by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction"),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransactionDTO> getTransactionById(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
        PaymentTransaction transaction = paymentTransactionService.getTransactionById(id);
        return ResponseEntity.ok(paymentTransactionMapper.toDto(transaction));
    }

    @Operation(summary = "Get transactions by order", description = "Retrieves all payment transactions for a specific order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentTransactionDTO>> getTransactionsByOrder(@PathVariable Long orderId) {
        List<PaymentTransaction> transactions = paymentTransactionService.getTransactionsByOrderId(orderId);
        List<PaymentTransactionDTO> dtos = transactions.stream()
            .map(paymentTransactionMapper::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get transactions by status", description = "Retrieves all payment transactions with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentTransactionDTO>> getTransactionsByStatus(
            @Parameter(description = "Transaction status", required = true) @PathVariable String status) {
        List<PaymentTransaction> transactions = paymentTransactionService.getTransactionsByStatus(status);
        List<PaymentTransactionDTO> dtos = transactions.stream()
            .map(paymentTransactionMapper::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get transactions by user", description = "Retrieves all payment transactions for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentTransactionDTO>> getTransactionsByUser(@PathVariable Long userId) {
        List<PaymentTransaction> transactions = paymentTransactionService.getTransactionsByUserId(userId);
        List<PaymentTransactionDTO> dtos = transactions.stream()
            .map(paymentTransactionMapper::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Create transaction", description = "Creates a new payment transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created transaction"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PaymentTransactionDTO> createTransaction(
            @Parameter(description = "Transaction details", required = true) @Valid @RequestBody PaymentTransactionDTO transactionDTO) {
        PaymentTransaction transaction = paymentTransactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(paymentTransactionMapper.toDto(transaction));
    }

    @Operation(summary = "Update transaction status", description = "Updates the status of an existing payment transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated transaction status"),
        @ApiResponse(responseCode = "400", description = "Invalid status"),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentTransactionDTO> updateTransactionStatus(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id,
            @Parameter(description = "New transaction status", required = true) @RequestParam String status) {
        PaymentTransaction transaction = paymentTransactionService.updateTransactionStatus(id, status);
        return ResponseEntity.ok(paymentTransactionMapper.toDto(transaction));
    }
} 