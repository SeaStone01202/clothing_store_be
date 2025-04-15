package com.java6.asm.clothing_store.dto.response;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double price;
}