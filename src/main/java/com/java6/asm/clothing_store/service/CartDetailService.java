package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.dto.response.CartDetailResponse;

public interface CartDetailService {

    CartDetailResponse addProductToCart(String accessToken, Integer productId, Integer quantity);

    CartDetailResponse updateQuantity(String accessToken, Integer cartDetailId, Integer quantity);

    boolean deleteCartDetail(Integer cartDetailId);
}