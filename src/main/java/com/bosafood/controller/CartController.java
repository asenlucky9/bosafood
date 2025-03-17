package com.bosafood.controller;

import com.bosafood.dto.ApiResponse;
import com.bosafood.dto.CartDTO;
import com.bosafood.dto.CartItemDTO;
import com.bosafood.model.Cart;
import com.bosafood.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
@Tag(name = "Shopping Cart Management", description = "APIs for managing user shopping carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get user's cart", description = "Retrieves the shopping cart for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        CartDTO cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Create cart", description = "Creates a new shopping cart for a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created cart"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Cart already exists for user"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<Cart> createCart(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(cartService.createCart(userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add item to cart", description = "Adds a menu item to the user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully added item to cart"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User or menu item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDTO> addItem(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemDTO itemDTO) {
        CartDTO updatedCart = cartService.addItem(userId, itemDTO);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "Update cart item", description = "Updates the quantity or special requests of an item in the cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated cart item"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User, cart, or item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartDTO> updateItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @Valid @RequestBody CartItemDTO itemDTO) {
        CartDTO updatedCart = cartService.updateItem(userId, itemId, itemDTO);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "Remove item from cart", description = "Removes a specific item from the user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully removed item from cart"),
        @ApiResponse(responseCode = "404", description = "User, cart, or item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CartDTO> removeItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        CartDTO updatedCart = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    @Operation(summary = "Clear cart", description = "Removes all items from the user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully cleared cart"),
        @ApiResponse(responseCode = "404", description = "User or cart not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(new ApiResponse<Void>(true, "Cart cleared successfully", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
} 