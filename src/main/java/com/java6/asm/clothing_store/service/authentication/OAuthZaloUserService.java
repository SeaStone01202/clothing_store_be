package com.java6.asm.clothing_store.service.authentication;

import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import com.java6.asm.clothing_store.dto.response.AuthResponse;
import com.java6.asm.clothing_store.dto.response.ZaloUserRegisterResponse;

public interface OAuthZaloUserService {

    ZaloUserRegisterResponse findOrCreateUser(String code);

    ZaloUserRegisterResponse createUser(ZaloUserRegisterResponse zaloUser, String email);

    ZaloUserRegisterResponse getUserInfoFromZalo(String accessToken);

    String getAccessTokenFromZalo(String code);
}
