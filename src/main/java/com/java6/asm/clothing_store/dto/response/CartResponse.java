package com.java6.asm.clothing_store.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private Integer id;
    private String email;
    private List<CartDetailResponse> cartDetails;
}