package com.bosafood.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Long totalUsers;
    private Long activeUsers;
    private List<MenuItemStatsDTO> popularItems;
    private List<OrderDTO> recentOrders;

    // Getters and Setters
    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public List<MenuItemStatsDTO> getPopularItems() {
        return popularItems;
    }

    public void setPopularItems(List<MenuItemStatsDTO> popularItems) {
        this.popularItems = popularItems;
    }

    public List<OrderDTO> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<OrderDTO> recentOrders) {
        this.recentOrders = recentOrders;
    }
} 