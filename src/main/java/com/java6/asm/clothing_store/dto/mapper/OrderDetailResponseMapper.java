package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.OrderDetailResponse;
import com.java6.asm.clothing_store.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailResponseMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}