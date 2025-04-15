package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.request.CategoryRequest;
import com.java6.asm.clothing_store.dto.response.CategoryResponse;
import com.java6.asm.clothing_store.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategories()));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesByStatus() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategoriesByStatus()));
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByCategoryName(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategory(request)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponse>> addCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.createCategory(request)));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.updateCategory(request)));
    }

    @PutMapping("/status")
    public ResponseEntity<ApiResponse<CategoryResponse>> changeStatusCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.changeStatus(request)));
    }
}
