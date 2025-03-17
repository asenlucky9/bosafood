package com.bosafood.repository;

import com.bosafood.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    Long countByMenuItemId(Long menuItemId);
    
    @Query("SELECT COALESCE(SUM(oi.quantity * oi.unitPrice), 0) FROM OrderItem oi WHERE oi.menuItem.id = :menuItemId")
    BigDecimal calculateTotalRevenueByMenuItemId(@Param("menuItemId") Long menuItemId);
} 