package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemUpdateRequestDto {
    private Long cartItemId;
    private Integer cartItemQuantity;
}