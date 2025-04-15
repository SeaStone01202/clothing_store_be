package com.java6.asm.clothing_store.dto.response;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserRegisterResponse {

    private String fullname;

    private String email;

    private RoleEnum role;

    private String image;

    private StatusEnum status;

    private TypeAccountEnum type;

}
