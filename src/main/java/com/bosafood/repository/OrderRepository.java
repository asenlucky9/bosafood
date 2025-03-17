package com.bosafood.repository;

import com.bosafood.model.Order;
import com.bosafood.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
    
    List<Order> findByStatusAndCreatedAtBetween(OrderStatus status, OffsetDateTime start, OffsetDateTime end);
    
    List<Order> findTop10ByOrderByCreatedAtDesc();
    
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    BigDecimal calculateTotalRevenue();
    
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = ?1")
    Order findByIdWithDetails(Long id);
    
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems")
    List<Order> findAllWithDetails();
    
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.user LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.menuItem WHERE o.user.id = :userId")
    List<Order> findByUser_Id(Long userId);
}
