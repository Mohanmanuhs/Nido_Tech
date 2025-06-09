package com.example.corpCartServer.service;


import com.example.corpCartServer.dto.CategoryDto;
import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryDto> getAllCategories() {
        return null;
    }

    public Category getCategoryById(Long id) {
        return null;
    }

    public Category createCategory(Category category) {
        return null;
    }

    public Optional<Object> updateCategory(Long id, Category category) {
        return Optional.empty();
    }

    public boolean deleteCategory(Long id) {
        return false;
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return null;
    }
}