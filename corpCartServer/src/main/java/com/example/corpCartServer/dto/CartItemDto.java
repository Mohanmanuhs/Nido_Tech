package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private String productImageUrl;

    private double cartItemPrice;

    private Integer cartItemQuantity;

}