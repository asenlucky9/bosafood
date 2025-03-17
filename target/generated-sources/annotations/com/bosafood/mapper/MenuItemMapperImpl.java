package com.bosafood.mapper;

import com.bosafood.dto.MenuItemDTO;
import com.bosafood.model.Category;
import com.bosafood.model.MenuItem;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T09:40:36-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class MenuItemMapperImpl implements MenuItemMapper {

    @Override
    public MenuItemDTO toDTO(MenuItem entity) {
        if ( entity == null ) {
            return null;
        }

        MenuItemDTO menuItemDTO = new MenuItemDTO();

        menuItemDTO.setCategoryId( entityCategoryId( entity ) );
        menuItemDTO.setAllergens( entity.getAllergens() );
        menuItemDTO.setCalories( entity.getCalories() );
        menuItemDTO.setDescription( entity.getDescription() );
        menuItemDTO.setId( entity.getId() );
        menuItemDTO.setImageUrl( entity.getImageUrl() );
        menuItemDTO.setIsAvailable( entity.getIsAvailable() );
        menuItemDTO.setIsSpicy( entity.getIsSpicy() );
        menuItemDTO.setIsVegetarian( entity.getIsVegetarian() );
        menuItemDTO.setName( entity.getName() );
        menuItemDTO.setPreparationTime( entity.getPreparationTime() );
        menuItemDTO.setPrice( entity.getPrice() );

        return menuItemDTO;
    }

    @Override
    public MenuItem toEntity(MenuItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MenuItem menuItem = new MenuItem();

        menuItem.setAllergens( dto.getAllergens() );
        menuItem.setCalories( dto.getCalories() );
        menuItem.setDescription( dto.getDescription() );
        menuItem.setId( dto.getId() );
        menuItem.setImageUrl( dto.getImageUrl() );
        menuItem.setIsAvailable( dto.getIsAvailable() );
        menuItem.setIsSpicy( dto.getIsSpicy() );
        menuItem.setIsVegetarian( dto.getIsVegetarian() );
        menuItem.setName( dto.getName() );
        menuItem.setPreparationTime( dto.getPreparationTime() );
        menuItem.setPrice( dto.getPrice() );

        return menuItem;
    }

    @Override
    public void updateEntity(MenuItemDTO dto, MenuItem entity) {
        if ( dto == null ) {
            return;
        }

        entity.setAllergens( dto.getAllergens() );
        entity.setCalories( dto.getCalories() );
        entity.setDescription( dto.getDescription() );
        entity.setImageUrl( dto.getImageUrl() );
        entity.setIsAvailable( dto.getIsAvailable() );
        entity.setIsSpicy( dto.getIsSpicy() );
        entity.setIsVegetarian( dto.getIsVegetarian() );
        entity.setName( dto.getName() );
        entity.setPreparationTime( dto.getPreparationTime() );
        entity.setPrice( dto.getPrice() );
    }

    private Long entityCategoryId(MenuItem menuItem) {
        if ( menuItem == null ) {
            return null;
        }
        Category category = menuItem.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
