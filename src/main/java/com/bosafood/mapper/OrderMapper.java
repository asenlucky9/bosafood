package com.bosafood.mapper;

import com.bosafood.dto.OrderDTO;
import com.bosafood.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phone")
    @Mapping(source = "items", target = "orderItems")
    @Mapping(source = "status", target = "orderStatus")
    OrderDTO toDTO(Order order);

    @Mapping(target = "user", ignore = true)
    @Mapping(source = "orderItems", target = "items")
    @Mapping(source = "orderStatus", target = "status")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "orderItems", target = "items")
    @Mapping(source = "orderStatus", target = "status")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(OrderDTO orderDTO, @MappingTarget Order order);
} 