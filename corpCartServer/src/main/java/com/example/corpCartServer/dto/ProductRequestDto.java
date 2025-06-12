package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String productName;

    private String productDescription;

    private double productPrice;

    private String productImageUrl;

    private Long categoryId;
}