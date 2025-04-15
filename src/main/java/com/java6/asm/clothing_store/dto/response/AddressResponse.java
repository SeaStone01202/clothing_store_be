package com.java6.asm.clothing_store.dto.response;

import com.java6.asm.clothing_store.constance.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    private Integer id;
    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private StatusEnum status;
    private Boolean isDefault;
    private String email;
}