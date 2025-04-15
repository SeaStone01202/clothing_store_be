package com.java6.asm.clothing_store.dto.response;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import lombok.*;

@Getter
@Setter
@Builder
public class UserResponse {

    private String email;

    private String fullname;

    private String image;

    private RoleEnum role;

    private String phone;

    private TypeAccountEnum type;

    private StatusEnum status;

}
