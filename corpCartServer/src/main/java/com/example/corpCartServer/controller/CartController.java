package com.example.corpCartServer.controller;


import com.example.corpCartServer.dto.CartDto;
import com.example.corpCartServer.dto.CartItemRequestDto;
import com.example.corpCartServer.dto.CartItemUpdateRequestDto;
import com.example.corpCartServer.mapper.CartMapper;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cart/")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        CartDto cartDto = CartMapper.cartToDto(cart,new CartDto());
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId,@RequestBody CartItemRequestDto dto) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, dto));
    }

    @PutMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(
            @PathVariable Long userId,
            @RequestBody CartItemUpdateRequestDto dto) {
        return ResponseEntity.ok(cartService.updateCartItem(userId,dto));
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Cart> removeItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, itemId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}
