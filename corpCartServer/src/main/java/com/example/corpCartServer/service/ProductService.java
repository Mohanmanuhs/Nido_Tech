package com.example.corpCartServer.service;


import com.example.corpCartServer.dto.PagedResponse;
import com.example.corpCartServer.dto.ProductDto;
import com.example.corpCartServer.dto.ProductRequestDto;
import com.example.corpCartServer.exception.ResourceAlreadyExistsException;
import com.example.corpCartServer.exception.ResourceNotFoundException;
import com.example.corpCartServer.mapper.ProductMapper;
import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.repository.ProductRepo;
import com.example.corpCartServer.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryService categoryService;

    public void createProduct(ProductRequestDto productDto) {
        Optional<Product> product1 = productRepo.findByProductName(productDto.getProductName());
        if (product1.isPresent()) {
            throw new ResourceAlreadyExistsException("product with this name already exists");
        }
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        Product product = ProductMapper.dtoToProduct(productDto, new Product(), category);
        productRepo.save(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepo.findAll().stream().map(product -> ProductMapper.productToDto(product, new ProductDto())).toList();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("product not found with this id");
        }
        return product.get();
    }

    public ProductDto getProductDtoById(Long id) {
        Product product = getProductById(id);
        return ProductMapper.productToDto(product, new ProductDto());
    }

    public ProductDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Product oldProduct = getProductById(productId);
        Category newCategory = categoryService.getCategoryById(productRequestDto.getCategoryId());
        Product newProduct = ProductMapper.dtoToProduct(productRequestDto, oldProduct, newCategory);
        newProduct = productRepo.save(newProduct);
        return ProductMapper.productToDto(newProduct, new ProductDto());
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepo.delete(product);
    }

    public void deactivateProduct(Long id) {
        Product product = getProductById(id);
        product.setIsDeleted(true);
        productRepo.save(product);
    }

    public PagedResponse<ProductDto> searchWithFilters(String name, Double minPrice, Double maxPrice, Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Product> spec = ProductSpecification.withFilters(name, minPrice, maxPrice, categoryId);
        Page<Product> productPage = productRepo.findAll(spec, pageable);
        Page<ProductDto> productDtos = productPage.map(product -> ProductMapper.productToDto(product, new ProductDto()));
        return new PagedResponse<>(productDtos.getContent(), productDtos.getNumber(), productDtos.getSize(), productDtos.getTotalElements(), productDtos.getTotalPages(), productDtos.isLast());
    }
}