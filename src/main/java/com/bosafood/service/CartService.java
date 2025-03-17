package com.bosafood.service;

import com.bosafood.dto.CartDTO;
import com.bosafood.dto.CartItemDTO;
import com.bosafood.exception.ResourceNotFoundException;
import com.bosafood.model.*;
import com.bosafood.repository.CartItemRepository;
import com.bosafood.repository.CartRepository;
import com.bosafood.repository.MenuItemRepository;
import com.bosafood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public CartDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCart(userId));
        return convertToDTO(cart);
    }

    @Transactional
    public CartDTO addItem(Long userId, CartItemDTO itemDTO) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCart(userId));

        MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(itemDTO.getMenuItemId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setMenuItem(menuItem);
                    return newItem;
                });

        cartItem.setQuantity(itemDTO.getQuantity());
        cartItem.setUnitPrice(menuItem.getPrice());
        cartItem.setSpecialInstructions(itemDTO.getSpecialInstructions());

        if (!cart.getItems().contains(cartItem)) {
            cart.addItem(cartItem);
        }

        cart = cartRepository.save(cart);
        return convertToDTO(cart);
    }

    @Transactional
    public CartDTO updateItem(Long userId, Long itemId, CartItemDTO itemDTO) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartItem.setQuantity(itemDTO.getQuantity());
        cartItem.setSpecialInstructions(itemDTO.getSpecialInstructions());

        cart = cartRepository.save(cart);
        return convertToDTO(cart);
    }

    @Transactional
    public CartDTO removeItem(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cart.removeItem(cartItem);
        cart = cartRepository.save(cart);
        return convertToDTO(cart);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setItems(cart.getItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        dto.setTotalAmount(calculateTotal(cart.getItems()));
        return dto;
    }

    private CartItemDTO convertToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setMenuItemId(item.getMenuItem().getId());
        dto.setMenuItemName(item.getMenuItem().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setSubtotal(item.getSubtotal());
        dto.setSpecialInstructions(item.getSpecialInstructions());
        return dto;
    }

    private BigDecimal calculateTotal(List<CartItem> items) {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 