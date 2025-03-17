package com.bosafood.mapper;

import com.bosafood.dto.OrderItemDTO;
import com.bosafood.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "menuItem.id", target = "menuItemId")
    @Mapping(source = "menuItem.name", target = "menuItemName")
    @Mapping(target = "subtotal", expression = "java(orderItem.getSubtotal())")
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(target = "menuItem", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderItem toEntity(OrderItemDTO orderItemDTO);
} 