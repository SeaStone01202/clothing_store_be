package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.CartDetailResponse;
import com.java6.asm.clothing_store.entity.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartDetailMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productDescription", source = "product.description")
    @Mapping(target = "productImageUrl", source = "product.imageUrl")
    @Mapping(target = "productStock", source = "product.stock")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "productCategory", source = "product.category.name")
    CartDetailResponse toResponse(CartDetail cartDetail);

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartDetail toEntity(CartDetailResponse cartDetailResponse);
}