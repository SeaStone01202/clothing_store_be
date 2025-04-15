package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.response.ProductResponse;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> retrieveAllProducts(@RequestParam int page) {
        return ResponseEntity.ok(ApiResponse.success(productService.findAll(page)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> retrieveProductByCategory(@PathVariable String category, @RequestParam  int page) {
        return ResponseEntity.ok(ApiResponse.success(productService.findByCategory(category, page)));
    }

    @GetMapping("/category/price/{category}")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> retrieveProductByCategoryAndPrice(@PathVariable String category, @RequestParam double min, @RequestParam double max, @RequestParam  int page) {
        return ResponseEntity.ok(ApiResponse.success(productService.findByCategoryAndPrice(min, max, category, page)));
    }

    @GetMapping("/price")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> retrieveProductsByPrice(
            @RequestParam double min,
            @RequestParam double max,
            @RequestParam int page) {
        return ResponseEntity.ok(ApiResponse.success(productService.findByPrice(min, max, page)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> searchProductsByName(
            @RequestParam String keyword,
            @RequestParam int page) {
        return ResponseEntity.ok(ApiResponse.success(productService.findByName(keyword, page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable Integer id) {
        ProductResponse product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.ok(ApiResponse.error(new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
        }
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @GetMapping("/{id}/related")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getRelatedProducts(
            @PathVariable Integer id,
            @RequestParam String category,
            @RequestParam int page) {
        return ResponseEntity.ok(ApiResponse.success(productService.findRelatedProducts(category, id, page)));
    }

    @GetMapping("/countStock")
    public ResponseEntity<ApiResponse<Integer>> countProductsStock() {
        return ResponseEntity.ok(ApiResponse.success(productService.countProductsStock()));
    }
}
