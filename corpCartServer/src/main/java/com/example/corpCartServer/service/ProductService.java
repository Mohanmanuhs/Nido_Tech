package com.example.corpCartServer.service;


import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Product updateProduct(Long id, Product updatedProduct) {
        return null;
    }

    public void deleteProduct(Long id) {

    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return null;
    }
}