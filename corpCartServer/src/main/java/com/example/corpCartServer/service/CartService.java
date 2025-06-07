package com.example.corpCartServer.service;


import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.CartItem;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.repository.CartItemRepo;
import com.example.corpCartServer.repository.CartRepo;
import com.example.corpCartServer.repository.ProductRepo;
import com.example.corpCartServer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepo.findByCustomer_UserId(userId);
    }

    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepo.findByCustomer_UserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer((Customer) UserRepo.findById(userId).orElseThrow());
                    return cartRepo.save(newCart);
                });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(productRepo.findById(productId).orElseThrow());
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        return cartRepo.save(cart);
    }

    public Cart updateCartItem(Long userId, Long itemId, int quantity) {
        Cart cart = cartRepo.findByCustomer_UserId(userId).orElseThrow();
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow();

        item.setQuantity(quantity);
        return cartRepo.save(cart);
    }

    public Cart removeItemFromCart(Long userId, Long itemId) {
        Cart cart = cartRepo.findByCustomer_UserId(userId).orElseThrow();
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        return cartRepo.save(cart);
    }

    public Cart clearCart(Long userId) {
        Cart cart = cartRepo.findByCustomer_UserId(userId).orElseThrow();
        cart.getItems().clear();
        return cartRepo.save(cart);
    }
}