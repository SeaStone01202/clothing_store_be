package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.constance.OrderStatusEnum;
import com.java6.asm.clothing_store.dto.request.OrderRequest;
import com.java6.asm.clothing_store.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(String accessToken, OrderRequest request);

    List<OrderResponse> getOrdersByEmail(String accessToken);

    Page<OrderResponse> retrieveAllOrder(int page);

    boolean updateOrder(Integer orderId, OrderStatusEnum status);

    Integer countOrder();

    Double countOrderPrice();
}