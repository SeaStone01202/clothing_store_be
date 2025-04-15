package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.response.CartResponse;
import com.java6.asm.clothing_store.entity.Cart;
import com.java6.asm.clothing_store.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@RequestHeader( value = "Authorization", required = false) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(accessToken)));
    }
}