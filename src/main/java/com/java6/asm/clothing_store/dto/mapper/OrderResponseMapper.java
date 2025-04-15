package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.OrderResponse;
import com.java6.asm.clothing_store.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { OrderDetailResponseMapper.class })
public interface OrderResponseMapper {

    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "createdAt", target = "createdAt")
    List<OrderResponse> toOrderResponse(List<Order> orders);
}
