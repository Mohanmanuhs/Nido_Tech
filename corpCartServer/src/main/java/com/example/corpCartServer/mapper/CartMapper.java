package com.example.corpCartServer.mapper;

import com.example.corpCartServer.dto.CartDto;
import com.example.corpCartServer.dto.CartItemDto;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.CartItem;

import java.util.List;

public class CartMapper {

    public static CartDto cartToDto(Cart cart, CartDto dto) {
        if (dto==null || cart == null) return null;
        dto.setTotalAmount(cart.getTotalAmount());
        List<CartItemDto> cartItemDtos = cart.getCartItems().stream().map(cartItem ->
                cartItemToDto(cartItem,new CartItemDto())
        ).toList();
        dto.setCartItemDtos(cartItemDtos);
        return dto;
    }

    public static CartItemDto cartItemToDto(CartItem cartItem, CartItemDto dto) {
        if (dto == null || cartItem == null) return null;
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setProductId(cartItem.getProduct().getProductId());
        dto.setCartItemQuantity(cartItem.getCartItemQuantity());
        dto.setProductName(cartItem.getProduct().getProductName());
        dto.setProductImageUrl(cartItem.getProduct().getProductImageUrl());
        dto.setCartItemPrice(cartItem.getCartItemPrice());
        return dto;
    }
}
