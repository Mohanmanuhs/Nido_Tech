package com.example.corpCartServer.service;


import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<Category> getAllCategories() {
        return null;
    }

    public Optional<Object> getCategoryById(Long id) {
        return Optional.empty();
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
}