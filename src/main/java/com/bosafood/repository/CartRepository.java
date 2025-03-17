package com.bosafood.repository;

import com.bosafood.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_Id(Long userId);
    boolean existsByUser_Id(Long userId);

    Optional<Cart> findByUserId(Long userId);
}