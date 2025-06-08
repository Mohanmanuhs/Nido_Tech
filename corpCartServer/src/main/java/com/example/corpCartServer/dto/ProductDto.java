package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productName;

    private String productDescription;

    private double productPrice;

    private String productImageUrl;

    private CategoryDto categoryDto;
}