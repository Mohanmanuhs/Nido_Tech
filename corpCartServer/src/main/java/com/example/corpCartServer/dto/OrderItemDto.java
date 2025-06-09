package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long orderItemId;

    private Integer orderItemQuantity;

    private Double orderItemPrice;

    private Long productId;

    private String productName;

    private String productImageUrl;

}