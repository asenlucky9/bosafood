package com.bosafood.controller;

import com.bosafood.dto.MenuItemDTO;
import com.bosafood.model.MenuItem;
import com.bosafood.service.MenuItemService;
import com.bosafood.dto.mapper.MenuItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu-items")
@Tag(name = "Menu Items", description = "APIs for managing menu items")
@CrossOrigin(origins = "*")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @GetMapping
    @Operation(summary = "Get all menu items", description = "Retrieves a list of all menu items in the system")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        List<MenuItem> items = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu item by ID", description = "Retrieves a specific menu item by its ID")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
        MenuItem item = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(menuItemMapper.toDto(item));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get menu items by category", description = "Retrieves all menu items in a specific category")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByCategory(@PathVariable Long categoryId) {
        List<MenuItem> items = menuItemService.getMenuItemsByCategory(categoryId);
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available menu items", description = "Retrieves all currently available menu items")
    public ResponseEntity<List<MenuItemDTO>> getAvailableMenuItems() {
        List<MenuItem> items = menuItemService.getAvailableMenuItems();
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/available/category/{categoryId}")
    @Operation(summary = "Get available menu items by category", description = "Retrieves all available menu items in a specific category")
    public ResponseEntity<List<MenuItemDTO>> getAvailableMenuItemsByCategory(@PathVariable Long categoryId) {
        List<MenuItem> items = menuItemService.getAvailableMenuItemsByCategory(categoryId);
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/vegetarian")
    @Operation(summary = "Get vegetarian menu items", description = "Retrieves all vegetarian menu items")
    public ResponseEntity<List<MenuItemDTO>> getVegetarianMenuItems() {
        List<MenuItem> items = menuItemService.getVegetarianMenuItems();
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/spicy")
    @Operation(summary = "Get spicy menu items", description = "Retrieves all spicy menu items")
    public ResponseEntity<List<MenuItemDTO>> getSpicyMenuItems() {
        List<MenuItem> items = menuItemService.getSpicyMenuItems();
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search menu items", description = "Searches menu items by name")
    public ResponseEntity<List<MenuItemDTO>> searchMenuItems(@RequestParam String name) {
        List<MenuItem> items = menuItemService.searchMenuItems(name);
        return ResponseEntity.ok(items.stream()
                .map(menuItemMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @Operation(summary = "Create menu item", description = "Creates a new menu item")
    public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItem item = menuItemService.createMenuItem(menuItemDTO);
        return ResponseEntity.ok(menuItemMapper.toDto(item));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update menu item", description = "Updates an existing menu item")
    public ResponseEntity<MenuItemDTO> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItem item = menuItemService.updateMenuItem(id, menuItemDTO);
        return ResponseEntity.ok(menuItemMapper.toDto(item));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete menu item", description = "Deletes a menu item")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }
} 