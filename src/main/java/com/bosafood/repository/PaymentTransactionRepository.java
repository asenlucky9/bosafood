package com.bosafood.repository;

import com.bosafood.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    @Query("SELECT pt FROM PaymentTransaction pt LEFT JOIN FETCH pt.order o LEFT JOIN FETCH o.user")
    List<PaymentTransaction> findAllWithDetails();

    @Query("SELECT pt FROM PaymentTransaction pt LEFT JOIN FETCH pt.order o LEFT JOIN FETCH o.user WHERE pt.transactionId = :id")
    Optional<PaymentTransaction> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT pt FROM PaymentTransaction pt LEFT JOIN FETCH pt.order o LEFT JOIN FETCH o.user WHERE o.user.id = :userId")
    List<PaymentTransaction> findByOrder_User_Id(@Param("userId") Long userId);
    
    @Query("SELECT pt FROM PaymentTransaction pt LEFT JOIN FETCH pt.order o LEFT JOIN FETCH o.user WHERE o.orderId = :orderId")
    List<PaymentTransaction> findByOrder_OrderId(@Param("orderId") Long orderId);
    
    @Query("SELECT pt FROM PaymentTransaction pt LEFT JOIN FETCH pt.order o LEFT JOIN FETCH o.user WHERE pt.status = :status")
    List<PaymentTransaction> findByStatus(@Param("status") String status);
}