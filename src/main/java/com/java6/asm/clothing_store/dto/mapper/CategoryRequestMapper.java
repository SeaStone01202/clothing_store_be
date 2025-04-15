package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.request.CategoryRequest;
import com.java6.asm.clothing_store.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {

    CategoryRequest toResponse(Category category);
}
