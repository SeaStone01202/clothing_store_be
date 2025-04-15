package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.dto.response.CartResponse;

public interface CartService {

    CartResponse getCart(String userName);

}
