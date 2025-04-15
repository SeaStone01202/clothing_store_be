package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.request.OrderRequest;
import com.java6.asm.clothing_store.dto.request.OrderStatusRequest;
import com.java6.asm.clothing_store.dto.response.OrderResponse;
import com.java6.asm.clothing_store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestHeader( value = "Authorization", required = false) String authorizationHeader,
            @RequestBody OrderRequest request) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(orderService.createOrder(accessToken, request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByEmail (
            @RequestHeader( value = "Authorization", required = false) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrdersByEmail(accessToken)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> getAllOrders (int page) {
        return ResponseEntity.ok(ApiResponse.success(orderService.retrieveAllOrder(page)));
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders (String email) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrdersByEmail(email)));
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<Boolean>> changeStatus (@RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success(orderService.updateOrder(request.getOrderId(), request.getStatus())));
    }

    @GetMapping("/countOrder")
    public ResponseEntity<ApiResponse<Integer>> countOrder () {
        return ResponseEntity.ok(ApiResponse.success(orderService.countOrder()));
    }

    @GetMapping("/countPrice")
    public ResponseEntity<ApiResponse<Double>> countOrderPrice () {
        return ResponseEntity.ok(ApiResponse.success(orderService.countOrderPrice()));
    }
}