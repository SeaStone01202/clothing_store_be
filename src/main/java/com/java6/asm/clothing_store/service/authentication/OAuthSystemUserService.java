package com.java6.asm.clothing_store.service.authentication;

import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.dto.response.AuthResponse;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuthSystemUserService {

    AuthResponse authenticateAndGenerateTokens(UserRequest request, HttpServletResponse response);

    AuthResponse refreshAccessToken(String refreshToken);

    String logout(String refreshToken, HttpServletResponse response);

    UserResponse getUser(String authorizationHeader);
}