package com.example.corpCartServer.service;


import com.example.corpCartServer.dto.CategoryDto;
import com.example.corpCartServer.mapper.CategoryMapper;
import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.CategoryRepo;
import com.example.corpCartServer.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream().
                map(category -> CategoryMapper.categoryToDto(category,new CategoryDto()))
                .toList();
    }

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).get();
    }

    public Category createCategory(Category category) {
        return null;
    }

    public Optional<Object> updateCategory(Long id, Category category) {
        return Optional.empty();
    }

    public void deleteCategory(Long categoryId) {
        Category categoryToDelete = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Category otherCategory = categoryRepo.findByCategoryName("Other")
                .orElseGet(() -> {
                    Category newOther = new Category();
                    newOther.setCategoryName("Other");
                    return categoryRepo.save(newOther);
                });

        for (Product product : categoryToDelete.getProducts()) {
            product.setCategory(otherCategory);
        }

        productRepo.saveAll(categoryToDelete.getProducts());
        categoryRepo.delete(categoryToDelete);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return null;
    }
}