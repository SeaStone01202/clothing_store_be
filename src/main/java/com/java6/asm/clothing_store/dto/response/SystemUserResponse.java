package com.java6.asm.clothing_store.dto.response;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemUserResponse {

    private String email;

    private String image;

    private RoleEnum role;

    private TypeAccountEnum type;
}
