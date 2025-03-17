package com.bosafood.mapper;

import com.bosafood.dto.CategoryDTO;
import com.bosafood.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "menuItems", ignore = true)
    CategoryDTO toDTO(Category category);

    @Mapping(target = "menuItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menuItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(CategoryDTO categoryDTO, @MappingTarget Category category);
} 