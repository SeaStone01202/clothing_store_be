package com.java6.asm.clothing_store.service.authentication;

import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.dto.response.AuthResponse;
import com.java6.asm.clothing_store.dto.response.GoogleUserRegisterResponse;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthGoogleUserService {

    AuthResponse authenticateAndGenerateTokens(OAuth2User oauth2User, String deviceId, HttpServletResponse response);

    AuthResponse refreshAccessToken(String refreshToken);

    String logout(String refreshToken, HttpServletResponse response);

    UserResponse getUser(String authorizationHeader);

    UserRequest changeUserByOAuth2User(OAuth2User oAuth2User);

    GoogleUserRegisterResponse createUser(String email, String name, String image, TypeAccountEnum type);

    GoogleUserRegisterResponse findOrCreateUser(String email, String name, String image, TypeAccountEnum type);

    GoogleUserRegisterResponse getUserByEmail(String email);

}
