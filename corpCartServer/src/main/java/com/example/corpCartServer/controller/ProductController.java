package com.example.corpCartServer.controller;

import com.example.corpCartServer.dto.PagedResponse;
import com.example.corpCartServer.dto.ProductDto;
import com.example.corpCartServer.dto.ProductRequestDto;
import com.example.corpCartServer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productDto) {
        productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductDtoById(productId);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto updatedProductDto) {
        ProductDto productDto = productService.updateProduct(id,updatedProductDto);
        return ResponseEntity.ok("product Updated successfully");
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok("product deactivated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("product deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<ProductDto> pagedResponse = productService.searchWithFilters(name,minPrice,maxPrice,categoryId,page,size);
        return ResponseEntity.ok(pagedResponse);
    }

}
