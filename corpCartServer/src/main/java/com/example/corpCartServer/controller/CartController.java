package com.example.corpCartServer.controller;


import com.example.corpCartServer.dto.CartDto;
import com.example.corpCartServer.dto.CartItemRequestDto;
import com.example.corpCartServer.dto.CartItemUpdateRequestDto;
import com.example.corpCartServer.models.auth.UserPrincipal;
import com.example.corpCartServer.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cart/")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserPrincipal userDetails) {
        CartDto cartDto = cartService.getCartDto(userDetails);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/item")
    public ResponseEntity<?> addItemToCart(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody CartItemRequestDto dto) {
        cartService.addItemToCart(userDetails, dto);
        return ResponseEntity.ok("added successfully");
    }

    @PutMapping("/item")
    public ResponseEntity<?> updateCartItem(@AuthenticationPrincipal UserPrincipal userDetails, @RequestBody CartItemUpdateRequestDto dto) {
        cartService.updateCartItem(userDetails, dto);
        return ResponseEntity.ok("updated successfully");
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> removeItem(@AuthenticationPrincipal UserPrincipal userDetails, @PathVariable Long itemId) {
        cartService.removeItemFromCart(userDetails, itemId);
        return ResponseEntity.ok("removed successfully");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal UserPrincipal userDetails) {
        cartService.clearCart(userDetails);
        return ResponseEntity.ok("cleared cart");
    }
}
