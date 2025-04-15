package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.ProductResponse;
import com.java6.asm.clothing_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "category.name", target = "category")
    ProductResponse toResponse(Product product);

//    Product toEntity(ProductResponse productResponse);
}
