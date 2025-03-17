package com.bosafood.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "special_offers")
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "discount_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(name = "terms_and_conditions", length = 1000)
    private String termsAndConditions;

    @Column(name = "minimum_order_value")
    private Integer minimumOrderValue;

    @Column(name = "maximum_discount")
    private Integer maximumDiscount;

    @ManyToMany
    @JoinTable(
        name = "special_offer_menu_items",
        joinColumns = @JoinColumn(name = "special_offer_id"),
        inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private Set<MenuItem> menuItems = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
} 