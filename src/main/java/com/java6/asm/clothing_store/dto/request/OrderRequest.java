package com.java6.asm.clothing_store.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String name;

    private String phone;

    private Integer addressId;

    private String paymentMethod;

    private List<OrderDetailRequest> orderDetails;
}