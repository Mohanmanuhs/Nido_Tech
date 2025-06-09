package com.example.corpCartServer.controller;

import com.example.corpCartServer.dto.ProductDto;
import com.example.corpCartServer.mapper.ProductMapper;
import com.example.corpCartServer.models.Category;
import com.example.corpCartServer.models.Product;
import com.example.corpCartServer.service.CategoryService;
import com.example.corpCartServer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create/{categoryId}")
    public ResponseEntity<Product> createProduct(
            @PathVariable Long categoryId,
            @RequestBody ProductDto productDto
    ) {
        Category category = categoryService.getCategoryById(categoryId);
        Product product = ProductMapper.dtoToProduct(productDto,new Product(),category);
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts().stream().map(product->ProductMapper.productToDto(product,new ProductDto())).toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductMapper.productToDto(product,new ProductDto()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProductDto) {
        Product oldProduct = productService.getProductById(id);
        Product newProduct = ProductMapper.dtoToProduct(updatedProductDto,oldProduct,oldProduct.getCategory());
        Product product = productService.updateProduct(id, newProduct);
        return ResponseEntity.ok(ProductMapper.productToDto(product,new ProductDto()));
    }

    @PutMapping("changeCategory/{productId}/{categoryId}")
    public ResponseEntity<ProductDto> changeProductCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        Product oldProduct = productService.getProductById(productId);
        Category newCategory = categoryService.getCategoryById(categoryId);
        oldProduct.setCategory(newCategory);
        productService.updateProduct(productId,oldProduct);
        return ResponseEntity.ok(ProductMapper.productToDto(oldProduct,new ProductDto()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productService.searchWithFilters(name, minPrice, maxPrice, categoryId, pageable);

        Page<ProductDto> dtoPage = productPage.map(product->ProductMapper.productToDto(product,new ProductDto()));

        return ResponseEntity.ok(dtoPage);
    }

}
