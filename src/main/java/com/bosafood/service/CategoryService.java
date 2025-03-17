package com.bosafood.service;

import com.bosafood.dto.CategoryDTO;
import com.bosafood.dto.mapper.CategoryMapper;
import com.bosafood.model.Category;
import com.bosafood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<CategoryDTO> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
        return categoryMapper.toDto(category);
    }

    public List<CategoryDTO> searchCategories(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return categoryRepository.findByNameContainingIgnoreCase(name.trim()).stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        validateCategoryDTO(categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        validateCategoryDTO(categoryDTO);
        Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        
        Category updatedCategory = categoryMapper.toEntity(categoryDTO);
        updatedCategory.setId(id); // Ensure we keep the same ID
        
        Category savedCategory = categoryRepository.save(updatedCategory);
        return categoryMapper.toDto(savedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private void validateCategoryDTO(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("Category data cannot be null");
        }
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }
        String name = categoryDTO.getName().trim();
        if (name.length() > 100) {
            throw new IllegalArgumentException("Category name cannot exceed 100 characters");
        }
        if (categoryDTO.getDescription() != null && categoryDTO.getDescription().length() > 500) {
            throw new IllegalArgumentException("Category description cannot exceed 500 characters");
        }
    }
} 