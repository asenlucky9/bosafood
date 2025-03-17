package com.bosafood.dto;

import java.util.List;

public class UserDetailsDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean active;
    private List<OrderDTO> orderHistory;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<OrderDTO> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderDTO> orderHistory) {
        this.orderHistory = orderHistory;
    }
} 