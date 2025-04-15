package com.java6.asm.clothing_store.dto.response;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponse {
    private Integer id;
    private LocalDate createdAt;
    private String paymentMethod;
    private String status;
    private AddressResponse address;
    private List<OrderDetailResponse> orderDetails;
}