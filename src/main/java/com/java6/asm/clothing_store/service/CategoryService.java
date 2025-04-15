package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.dto.request.CategoryRequest;
import com.java6.asm.clothing_store.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategory(CategoryRequest request);

    CategoryResponse updateCategory(CategoryRequest request);

    CategoryResponse changeStatus(CategoryRequest request);

    List<CategoryResponse> getCategories();

    List<CategoryResponse> getCategoriesByStatus();
}
