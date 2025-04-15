package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.request.AddressRequest;
import com.java6.asm.clothing_store.dto.response.AddressResponse;
import com.java6.asm.clothing_store.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAllAddresses(@RequestHeader( value = "Authorization", required = false) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ApiResponse.success(addressService.getAllAddresses(accessToken)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(@RequestBody AddressRequest request) {
        return ResponseEntity.ok(ApiResponse.success(addressService.createAddress(request)));
    }
}