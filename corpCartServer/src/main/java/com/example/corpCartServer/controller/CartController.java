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
    public ResponseEntity<?> addItemToCart(@PathVariable Long userId,@RequestBody CartItemRequestDto dto) {
        cartService.addItemToCart(userId, dto);
        return ResponseEntity.ok("added successfully");
    }

    @PutMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long userId,
            @RequestBody CartItemUpdateRequestDto dto) {
        cartService.updateCartItem(userId,dto);
        return ResponseEntity.ok("updated successfully");
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<?> removeItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok("removed successfully");
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("cleared cart");
    }
}
