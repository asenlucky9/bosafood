package com.bosafood.service;

import com.bosafood.dto.MenuItemDTO;
import com.bosafood.dto.MenuItemStatsDTO;
import com.bosafood.mapper.MenuItemMapper;
import com.bosafood.model.MenuItem;
import com.bosafood.model.Category;
import com.bosafood.repository.MenuItemRepository;
import com.bosafood.repository.CategoryRepository;
import com.bosafood.repository.OrderItemRepository;
import com.bosafood.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemMapper menuItemMapper;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, 
                         CategoryRepository categoryRepository,
                         MenuItemMapper menuItemMapper) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Transactional
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        Category category = categoryRepository.findById(menuItemDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
        menuItem.setCategory(category);
        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toDTO(menuItem);
    }

    @Transactional(readOnly = true)
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(menuItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MenuItemDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        return menuItemMapper.toDTO(menuItem);
    }

    @Transactional
    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        if (!menuItem.getCategory().getId().equals(menuItemDTO.getCategoryId())) {
            Category newCategory = categoryRepository.findById(menuItemDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            menuItem.setCategory(newCategory);
        }

        menuItemMapper.updateEntity(menuItemDTO, menuItem);
        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toDTO(menuItem);
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MenuItemDTO> getMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategory_Id(categoryId).stream()
                .map(menuItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItem> getAvailableMenuItems() {
        return menuItemRepository.findByIsAvailable(true);
    }

    public List<MenuItem> getAvailableMenuItemsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        return menuItemRepository.findByCategoryAndIsAvailable(category, true);
    }

    public List<MenuItem> getVegetarianMenuItems() {
        return menuItemRepository.findByIsVegetarian(true);
    }

    public List<MenuItem> getSpicyMenuItems() {
        return menuItemRepository.findByIsSpicy(true);
    }

    public List<MenuItem> searchMenuItems(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return menuItemRepository.findByNameContainingIgnoreCase(name.trim());
    }

    public List<MenuItemStatsDTO> getPopularItems(int limit) {
        return menuItemRepository.findAll().stream()
                .map(this::calculateMenuItemStats)
                .sorted((a, b) -> b.getOrderCount().compareTo(a.getOrderCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<MenuItemStatsDTO> getMenuItemStats() {
        return menuItemRepository.findAll().stream()
                .map(this::calculateMenuItemStats)
                .collect(Collectors.toList());
    }

    private MenuItemStatsDTO calculateMenuItemStats(MenuItem menuItem) {
        MenuItemStatsDTO stats = new MenuItemStatsDTO();
        stats.setMenuItemId(menuItem.getId());
        stats.setName(menuItem.getName());
        
        // Calculate order count and revenue
        Long orderCount = orderItemRepository.countByMenuItemId(menuItem.getId());
        BigDecimal totalRevenue = orderItemRepository.calculateTotalRevenueByMenuItemId(menuItem.getId());
        stats.setOrderCount(orderCount);
        stats.setTotalRevenue(totalRevenue);
        
        // Calculate review statistics
        Double averageRating = reviewRepository.calculateAverageRatingByMenuItemId(menuItem.getId());
        Integer reviewCount = reviewRepository.countByMenuItemId(menuItem.getId());
        stats.setAverageRating(averageRating);
        stats.setReviewCount(reviewCount);
        
        return stats;
    }
} 