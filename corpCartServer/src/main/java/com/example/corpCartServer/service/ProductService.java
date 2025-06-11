package com.example.corpCartServer.service;


import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.ProductRepo;
import com.example.corpCartServer.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;


    public Product createProduct(Product product) {
        return null;
    }

    public List<Product> getAllProducts() {
        return null;
    }

    public Product getProductById(Long id) {
        return null;
    }

    public Product updateProduct(Product updatedProduct) {
        return productRepo.save(updatedProduct);
    }

    public void deleteProduct(Long id) {

    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return null;
    }
    public Page<Product> searchWithFilters(String name, Double minPrice, Double maxPrice, Long categoryId, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.withFilters(name, minPrice, maxPrice, categoryId);
        return productRepo.findAll(spec, pageable);
    }
}