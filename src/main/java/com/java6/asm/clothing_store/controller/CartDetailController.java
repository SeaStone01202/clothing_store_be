package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.response.CartDetailResponse;
import com.java6.asm.clothing_store.service.CartDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/details")
@AllArgsConstructor
public class CartDetailController {

    private final CartDetailService cartDetailService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartDetailResponse>> addProductToCart(
            @RequestHeader( value = "Authorization", required = false) String authorizationHeader,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(cartDetailService.addProductToCart(accessToken, productId, quantity)));
    }

    @PutMapping("/{cartDetailId}")
    public ResponseEntity<ApiResponse<CartDetailResponse>> updateQuantity(
            @RequestHeader( value = "Authorization", required = false) String authorizationHeader,
            @PathVariable Integer cartDetailId,
            @RequestParam Integer quantity) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(cartDetailService.updateQuantity(accessToken, cartDetailId, quantity)));
    }

    @DeleteMapping("/{cartDetailId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteCartDetail(@PathVariable Integer cartDetailId) {
        return ResponseEntity.ok(ApiResponse.success(cartDetailService.deleteCartDetail(cartDetailId)));
    }
}