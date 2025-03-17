package com.bosafood.mapper;

import com.bosafood.dto.UserDTO;
import com.bosafood.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateEntity(UserDTO userDTO, @MappingTarget User user);
} 