package com.bosafood.dto.mapper;

import com.bosafood.dto.OrderDTO;
import com.bosafood.dto.OrderItemDTO;
import com.bosafood.model.Order;
import com.bosafood.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    public OrderDTO toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setCustomerName(order.getCustomerName());
        dto.setEmail(order.getCustomerEmail());
        dto.setPhone(order.getCustomerPhone());
        dto.setDelivery(order.getDeliveryMethod());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        
        if (order.getOrderItems() != null) {
            List<OrderItemDTO> itemDtos = order.getOrderItems().stream()
                .map(this::toOrderItemDto)
                .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }
        
        return dto;
    }
    
    private OrderItemDTO toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setMenuItemId(orderItem.getMenuItem().getId());
        dto.setMenuItemName(orderItem.getMenuItem().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setSubtotal(orderItem.getSubtotal());
        
        return dto;
    }
} 