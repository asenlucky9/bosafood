package com.bosafood.dto.mapper;

import com.bosafood.dto.MenuItemDTO;
import com.bosafood.model.MenuItem;
import com.bosafood.model.Category;
import com.bosafood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper implements EntityMapper<MenuItemDTO, MenuItem> {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public MenuItem toEntity(MenuItemDTO dto) {
        if (dto == null) {
            return null;
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setImageUrl(dto.getImageUrl());
        
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
            menuItem.setCategory(category);
        }
        
        menuItem.setIsVegetarian(dto.getIsVegetarian() != null ? dto.getIsVegetarian() : false);
        menuItem.setIsSpicy(dto.getIsSpicy() != null ? dto.getIsSpicy() : false);
        menuItem.setIsAvailable(dto.getIsAvailable() != null ? dto.getIsAvailable() : true);
        menuItem.setPreparationTime(dto.getPreparationTime());
        menuItem.setCalories(dto.getCalories());
        menuItem.setAllergens(dto.getAllergens());
        
        return menuItem;
    }

    @Override
    public MenuItemDTO toDto(MenuItem entity) {
        if (entity == null) {
            return null;
        }

        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        
        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
            dto.setCategoryName(entity.getCategory().getName());
        }
        
        dto.setIsVegetarian(entity.getIsVegetarian());
        dto.setIsSpicy(entity.getIsSpicy());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setPreparationTime(entity.getPreparationTime());
        dto.setCalories(entity.getCalories());
        dto.setAllergens(entity.getAllergens());
        
        return dto;
    }
} 