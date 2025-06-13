package com.example.corpCartServer.service;


import com.example.corpCartServer.dto.CartDto;
import com.example.corpCartServer.dto.CartItemRequestDto;
import com.example.corpCartServer.dto.CartItemUpdateRequestDto;
import com.example.corpCartServer.exception.ResourceNotFoundException;
import com.example.corpCartServer.mapper.CartMapper;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.CartItem;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.CartItemRepo;
import com.example.corpCartServer.repository.CartRepo;
import com.example.corpCartServer.repository.CustomerRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepo cartRepo;

    private final CartItemRepo cartItemRepo;

    private final CustomerRepo customerRepo;

    private final ProductService productService;

    public CartService(CartRepo cartRepo, CartItemRepo cartItemRepo, CustomerRepo customerRepo, ProductService productService) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.customerRepo = customerRepo;
        this.productService = productService;
    }

    public Cart getCart(UserDetails userDetails) {
        String email = userDetails.getUsername();
        Optional<Cart> cart = customerRepo.findCartByEmail(email);
        if (cart.isEmpty()) {
            throw new ResourceNotFoundException("cart is not exist for this user");
        }
        return cart.get();
    }

    public CartItem getCartItem(String email, Long cartItemId) {
        Optional<CartItem> item = cartItemRepo.findByCartItemIdAndCart_Customer_Email(cartItemId, email);
        if (item.isEmpty()) {
            throw new ResourceNotFoundException("cart item is not found");
        }
        return item.get();
    }

    public CartDto getCartDto(UserDetails userDetails) {
        return CartMapper.cartToDto(getCart(userDetails), new CartDto());
    }

    public void addItemToCart(UserDetails userDetails, CartItemRequestDto dto) {
        Cart cart = getCart(userDetails);
        Product product = productService.getProductById(dto.getProductId());

        CartItem item = cart.getCartItems().stream().filter(ci -> ci.getProduct().getProductId().equals(dto.getProductId())).findFirst().orElseGet(() -> {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            return newItem;
        });
        item.setCartItemQuantity(dto.getCartItemQuantity());
        item.setCartItemPrice(dto.getCartItemQuantity() * product.getProductPrice());
        cartItemRepo.save(item);
    }

    public void updateCartItem(UserDetails userDetails, CartItemUpdateRequestDto dto) {
        String email = userDetails.getUsername();
        CartItem item = getCartItem(email, dto.getCartItemId());
        item.setCartItemQuantity(dto.getCartItemQuantity());
        item.setCartItemPrice(dto.getCartItemQuantity() * item.getProduct().getProductPrice());
        cartItemRepo.save(item);
    }

    public void removeItemFromCart(UserDetails userDetails, Long itemId) {
        String email = userDetails.getUsername();
        CartItem item = getCartItem(email, itemId);
        cartItemRepo.delete(item);
    }

    public void clearCart(UserDetails userDetails) {
        Cart cart = getCart(userDetails);
        if (!cart.getCartItems().isEmpty()) {
            cart.getCartItems().clear();
        }
        cartRepo.save(cart);
    }
}