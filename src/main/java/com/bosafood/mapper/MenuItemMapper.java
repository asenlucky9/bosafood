package com.bosafood.mapper;

import com.bosafood.dto.MenuItemDTO;
import com.bosafood.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    
    @Mapping(target = "categoryId", source = "category.id")
    MenuItemDTO toDTO(MenuItem entity);

    @Mapping(target = "category", ignore = true)
    MenuItem toEntity(MenuItemDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(MenuItemDTO dto, @MappingTarget MenuItem entity);
} 