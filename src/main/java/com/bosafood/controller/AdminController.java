package com.bosafood.controller;

import com.bosafood.dto.*;
import com.bosafood.service.*;
import com.bosafood.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private ReviewService reviewService;

    // Dashboard Overview
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        DashboardDTO stats = new DashboardDTO();
        stats.setTotalOrders(orderService.getTotalOrders());
        stats.setTotalRevenue(orderService.getTotalRevenue());
        stats.setTotalUsers(userService.getTotalUsers());
        stats.setActiveUsers(userService.getActiveUsers());
        stats.setPopularItems(menuItemService.getPopularItems(5));
        stats.setRecentOrders(orderService.getRecentOrders(10));
        return ResponseEntity.ok(stats);
    }

    // Order Management
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(orderService.getAllOrdersWithFilters(status, startDate, endDate));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    // User Management
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserDetailsWithOrders(userId));
    }

    @PutMapping("/users/{userId}/status")
    public ResponseEntity<UserDTO> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody Map<String, Boolean> status) {
        return ResponseEntity.ok(userService.updateUserStatus(userId, status.get("active")));
    }

    // Menu Management
    @GetMapping("/menu-items/stats")
    public ResponseEntity<List<MenuItemStatsDTO>> getMenuItemStats() {
        return ResponseEntity.ok(menuItemService.getMenuItemStats());
    }

    // Review Management
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating) {
        return ResponseEntity.ok(reviewService.getAllReviewsWithFilters(minRating, maxRating));
    }

    // Sales Reports
    @GetMapping("/reports/sales")
    public ResponseEntity<SalesReportDTO> getSalesReport(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(orderService.generateSalesReport(startDate, endDate));
    }

    // Activity Logs
    @GetMapping("/activity-logs")
    public ResponseEntity<List<ActivityLogDTO>> getActivityLogs(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(userService.getActivityLogs(startDate, endDate));
    }
} 