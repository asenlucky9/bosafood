package com.bosafood.dto.mapper;

import com.bosafood.dto.CategoryDTO;
import com.bosafood.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements EntityMapper<CategoryDTO, Category> {
    
    @Override
    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return category;
    }

    @Override
    public CategoryDTO toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
} 