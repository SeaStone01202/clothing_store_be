package com.java6.asm.clothing_store.dto.request;

import lombok.Data;

@Data
public class OrderDetailRequest {

    private Integer productId;

    private Integer quantity;
    // Price present product
    private Double price;
}