package com.example.corpCartServer.mapper;

import com.example.corpCartServer.dto.CategoryDto;
import com.example.corpCartServer.dto.ProductDto;
import com.example.corpCartServer.dto.ProductRequestDto;
import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.models.Product;

public class ProductMapper {

    public static ProductDto productToDto(Product product,ProductDto dto) {
        if (product == null || dto == null) return null;

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductImageUrl(product.getProductImageUrl());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(product.getCategory().getCategoryId());
        categoryDto.setCategoryName(product.getCategory().getCategoryName());
        dto.setCategoryDto(categoryDto);
        return dto;
    }

    public static Product dtoToProduct(ProductRequestDto productDto, Product product, Category category) {
        if (productDto == null || product == null || category == null) return null;

        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductImageUrl(productDto.getProductImageUrl());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductImageUrl(productDto.getProductImageUrl());
        product.setCategory(category);
        return product;
    }
}
