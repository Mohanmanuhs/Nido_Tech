package com.example.corpCartServer.mapper;

import com.example.corpCartServer.dto.CategoryDto;
import com.example.corpCartServer.models.Category;

public class CategoryMapper {

    public static CategoryDto categoryToDto(Category category,CategoryDto dto) {
        if (dto==null || category == null) return null;
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryImage(category.getCategoryImage());
        return dto;
    }

    public static Category dtoToCategory(CategoryDto dto,Category category) {
        if (dto == null || category == null) return null;
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryImage(dto.getCategoryImage());
        return category;
    }
}
