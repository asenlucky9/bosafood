package com.bosafood.mapper;

import com.bosafood.dto.ReviewDTO;
import com.bosafood.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "menuItem.category.name", target = "menuItemCategory")
    @Mapping(source = "menuItem.id", target = "menuItemId")
    @Mapping(source = "menuItem.name", target = "menuItemName")
    ReviewDTO toDTO(Review review);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "menuItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review toEntity(ReviewDTO reviewDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "menuItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(ReviewDTO reviewDTO, @MappingTarget Review review);
} 