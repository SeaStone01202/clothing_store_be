package com.java6.asm.clothing_store.service.authentication;

import com.java6.asm.clothing_store.constance.RoleEnum;

public interface AccessTokenService {

    String generateToken(String username, RoleEnum role);

    String validateToken(String token);
}