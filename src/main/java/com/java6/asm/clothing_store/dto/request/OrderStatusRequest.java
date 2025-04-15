package com.java6.asm.clothing_store.dto.request;

import com.java6.asm.clothing_store.constance.OrderStatusEnum;
import lombok.Data;

@Data
public class OrderStatusRequest {
    private Integer orderId;
    private OrderStatusEnum status;
}
