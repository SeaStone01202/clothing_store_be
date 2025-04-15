package com.java6.asm.clothing_store.service.implement;

import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.mapper.CategoryResponseMapper;
import com.java6.asm.clothing_store.dto.request.CategoryRequest;
import com.java6.asm.clothing_store.dto.response.CategoryResponse;
import com.java6.asm.clothing_store.entity.Category;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.CategoryRepository;
import com.java6.asm.clothing_store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    public CategoryResponse createCategory(CategoryRequest request) {

        Category existingCategory = categoryRepository.findByName(request.getName()).orElse(null);

        if (existingCategory == null) {
            existingCategory = new Category();
            existingCategory.setName(request.getName());
            existingCategory.setStatus(StatusEnum.ACTIVE);

            return categoryResponseMapper.toResponse(categoryRepository.save(existingCategory));
        }

        throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
    }

    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    public CategoryResponse getCategory(CategoryRequest request) {
        return categoryRepository.findByName(request.getName())
                .map(category -> categoryResponseMapper.toResponse(category))
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    public CategoryResponse updateCategory(CategoryRequest request) {

        Category existingCategory = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        existingCategory.setName(request.getName());

        return categoryResponseMapper.toResponse(categoryRepository.save(existingCategory));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('DIRECTOR')")
    public CategoryResponse changeStatus(CategoryRequest request) {

        Category existingCategory = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (existingCategory.getStatus() == request.getStatus()) {
            return categoryResponseMapper.toResponse(existingCategory);
        }
        existingCategory.setStatus(request.getStatus());

        return categoryResponseMapper.toResponse(categoryRepository.save(existingCategory));
    }

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryResponseMapper::toResponse).toList();
    }

    @Override
    public List<CategoryResponse> getCategoriesByStatus() {
        return categoryRepository.findByStatus(StatusEnum.ACTIVE).stream().map(categoryResponseMapper::toResponse).toList();
    }
}
