package com.bosafood.controller;

import com.bosafood.dto.CategoryDTO;
import com.bosafood.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "APIs for managing menu categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves a list of all menu categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/active")
    @Operation(summary = "Get active categories", description = "Retrieves a list of all active menu categories")
    public ResponseEntity<List<CategoryDTO>> getActiveCategories() {
        return ResponseEntity.ok(categoryService.getActiveCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieves a specific category by its ID")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get category by name", description = "Retrieves a specific category by its name")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @GetMapping("/search")
    @Operation(summary = "Search categories", description = "Searches categories by name")
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.searchCategories(name));
    }

    @PostMapping
    @Operation(summary = "Create category", description = "Creates a new menu category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Updates an existing category")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Deletes a category")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
} 