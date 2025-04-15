package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.CategoryResponse;
import com.java6.asm.clothing_store.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);
}
