package com.example.corpCartServer.service;


import com.example.corpCartServer.dto.CategoryDto;
import com.example.corpCartServer.dto.ProductDto;
import com.example.corpCartServer.exception.ResourceAlreadyExistsException;
import com.example.corpCartServer.exception.ResourceNotFoundException;
import com.example.corpCartServer.mapper.CategoryMapper;
import com.example.corpCartServer.mapper.ProductMapper;
import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.CategoryRepo;
import com.example.corpCartServer.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream().map(category -> CategoryMapper.categoryToDto(category, new CategoryDto())).toList();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("category with this id not exist");
        }
        return category.get();
    }

    public CategoryDto getCategoryDtoById(Long id) {
        Category category = getCategoryById(id);
        return CategoryMapper.categoryToDto(category, new CategoryDto());
    }

    public void createCategory(CategoryDto categoryDto) {
        Optional<Category> category1 = categoryRepo.findByCategoryName(categoryDto.getCategoryName());
        if (category1.isPresent()) {
            throw new ResourceAlreadyExistsException("category with this name already exist");
        }
        Category category = CategoryMapper.dtoToCategory(categoryDto, new Category());
        categoryRepo.save(category);
    }

    public void updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> category1 = categoryRepo.findByCategoryName(categoryDto.getCategoryName());
        if (category1.isPresent()) {
            throw new ResourceAlreadyExistsException("category with this name already exist");
        }
        Category oldCategory = getCategoryById(id);
        oldCategory.setCategoryName(categoryDto.getCategoryName());
        categoryRepo.save(oldCategory);
    }

    public void deleteCategory(Long categoryId) {
        Category categoryToDelete = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Category otherCategory = categoryRepo.findByCategoryName("Other").orElseGet(() -> {
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

    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return category.getProducts().stream().map(product -> ProductMapper.productToDto(product, new ProductDto())).toList();
    }
}