package com.example.corpCartServer.service;


import com.example.corpCartServer.dto.CartItemRequestDto;
import com.example.corpCartServer.dto.CartItemUpdateRequestDto;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.CartItem;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.CartItemRepo;
import com.example.corpCartServer.repository.CartRepo;
import com.example.corpCartServer.repository.ProductRepo;
import com.example.corpCartServer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CartService {


    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserRepo UserRepo;

    @Autowired
    private ProductRepo productRepo;

    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByCustomer_UserId(userId);
    }

    public void addItemToCart(Long userId, CartItemRequestDto dto) {
        Cart cart = cartRepo.findByCustomer_UserId(userId);

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("product not found for user"));

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getProductId().equals(dto.getProductId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    return newItem;
                });
        item.setCartItemQuantity(dto.getCartItemQuantity());
        item.setCartItemPrice(dto.getCartItemQuantity()*product.getProductPrice());
        cartItemRepo.save(item);
    }

    public void updateCartItem(Long userId, CartItemUpdateRequestDto dto) {
        CartItem item = cartItemRepo.findByCart_Customer_UserIdAndCartItemId(userId, dto.getCartItemId())
                .orElseThrow(() -> new NoSuchElementException("CartItem not found for user"));

        item.setCartItemQuantity(dto.getCartItemQuantity());
        item.setCartItemPrice(dto.getCartItemQuantity()*item.getProduct().getProductPrice());
        cartItemRepo.save(item);
    }

    public void removeItemFromCart(Long userId, Long itemId) {
        CartItem item = cartItemRepo.findByCart_Customer_UserIdAndCartItemId(userId, itemId)
                .orElseThrow(() -> new NoSuchElementException("CartItem not found for user"));

        cartItemRepo.delete(item);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepo.findByCustomer_UserId(userId);
        if (!cart.getCartItems().isEmpty()) {
            cart.getCartItems().clear();
        }
        cartRepo.save(cart);
    }
}