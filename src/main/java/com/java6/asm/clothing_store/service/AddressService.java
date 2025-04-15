package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.dto.request.AddressRequest;
import com.java6.asm.clothing_store.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);

    List<AddressResponse> getAllAddresses(String email);
}