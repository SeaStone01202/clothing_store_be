package com.java6.asm.clothing_store.dto.request;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import lombok.Data;

@Data
public class ChangeRoleUserRequest {

    String emailUserChangeRole;

    RoleEnum role;

    StatusEnum status;
}
