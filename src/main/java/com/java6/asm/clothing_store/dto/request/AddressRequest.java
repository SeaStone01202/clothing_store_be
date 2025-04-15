package com.java6.asm.clothing_store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private String addressLine;
    private String ward; // Thêm ward
    private String district;
    private String city;
    private Boolean isDefault;
    private String email;
}