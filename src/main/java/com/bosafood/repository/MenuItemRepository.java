package com.bosafood.repository;

import com.bosafood.model.MenuItem;
import com.bosafood.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category")
    List<MenuItem> findAllWithCategory();
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.id = :id")
    Optional<MenuItem> findByIdWithCategory(Long id);
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.category = :category")
    List<MenuItem> findByCategory(Category category);
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.isAvailable = :isAvailable")
    List<MenuItem> findByIsAvailable(boolean isAvailable);
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.category = :category AND m.isAvailable = :isAvailable")
    List<MenuItem> findByCategoryAndIsAvailable(Category category, boolean isAvailable);
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.isVegetarian = :isVegetarian")
    List<MenuItem> findByIsVegetarian(boolean isVegetarian);
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE m.isSpicy = :isSpicy")
    List<MenuItem> findByIsSpicy(boolean isSpicy);
    
    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.category WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MenuItem> findByNameContainingIgnoreCase(String name);
} 