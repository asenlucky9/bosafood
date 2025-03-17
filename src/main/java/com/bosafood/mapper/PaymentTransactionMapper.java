package com.bosafood.mapper;

import com.bosafood.dto.PaymentTransactionDTO;
import com.bosafood.model.PaymentTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.user.firstName", target = "customerName")
    @Mapping(source = "order.user.email", target = "customerEmail")
    PaymentTransactionDTO toDTO(PaymentTransaction transaction);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentTransaction toEntity(PaymentTransactionDTO transactionDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(PaymentTransactionDTO transactionDTO, @MappingTarget PaymentTransaction transaction);
} 