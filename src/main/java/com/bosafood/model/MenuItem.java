package com.bosafood.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menu_items")
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "is_vegetarian")
    private Boolean isVegetarian = false;

    @Column(name = "is_spicy")
    private Boolean isSpicy = false;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "preparation_time")
    private Integer preparationTime;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "allergens", columnDefinition = "TEXT")
    private String allergens;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToMany(mappedBy = "menuItems")
    private Set<SpecialOffer> specialOffers = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isVegetarian == null) isVegetarian = false;
        if (isSpicy == null) isSpicy = false;
        if (isAvailable == null) isAvailable = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper methods for managing relationships
    public void addReview(Review review) {
        reviews.add(review);
        review.setMenuItem(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setMenuItem(null);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setMenuItem(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setMenuItem(null);
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setMenuItem(this);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setMenuItem(null);
    }

    public void addSpecialOffer(SpecialOffer specialOffer) {
        specialOffers.add(specialOffer);
        specialOffer.getMenuItems().add(this);
    }

    public void removeSpecialOffer(SpecialOffer specialOffer) {
        specialOffers.remove(specialOffer);
        specialOffer.getMenuItems().remove(this);
    }
} 