package com.java6.asm.clothing_store.service.authentication.impl;

import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.mapper.UserResponseMapper;
import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.dto.response.AuthResponse;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.authentication.AccessTokenService;
import com.java6.asm.clothing_store.service.authentication.DeviceManagementService;
import com.java6.asm.clothing_store.service.authentication.OAuthSystemUserService;
import com.java6.asm.clothing_store.service.authentication.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class OAuthSystemUserServiceImpl implements OAuthSystemUserService {

    private final UserRepository userRepository;

    private final UserResponseMapper userResponseMapper;

    private final PasswordEncoder passwordEncoder;

    private final AccessTokenService jwtAccessTokenService;

    private final RefreshTokenService jwtRefreshTokenService;

    private final DeviceManagementService deviceManagementService;

    @Override
    public AuthResponse authenticateAndGenerateTokens(UserRequest request, HttpServletResponse response) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmailAndStatus(request.getEmail(), StatusEnum.ACTIVE).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        String email = user.get().getEmail();

        String deviceId = request.getDeviceId();

        String refreshToken = deviceManagementService.getOrGenerateRefreshToken(email, deviceId);

        String accessToken = jwtAccessTokenService.generateToken(email.trim(), user.get().getRole());

        Cookie refreshTokenCookie = createRefreshTokenCookie(refreshToken);

        response.addCookie(refreshTokenCookie);

        return AuthResponse.builder().accessToken(accessToken).build();
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {

        String email = Optional
                .ofNullable(jwtRefreshTokenService
                        .validateToken(refreshToken))
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_TOKEN_INVALID));


        String newAccessToken = userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE)
                .map(user -> jwtAccessTokenService.generateToken(user.getEmail(), user.getRole()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return AuthResponse.builder().accessToken(newAccessToken).build();
    }

    @Override
    public String logout(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        boolean deleted = deviceManagementService.removeRefreshToken(refreshToken);
        if (deleted) {
            Cookie expiredCookie = createExpiredRefreshTokenCookie();
            response.addCookie(expiredCookie);
            return "Đăng xuất thành công";
        }

        throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
    }

    @Override
    public UserResponse getUser(String accessToken) {

        if (accessToken == null || accessToken.isBlank()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = jwtAccessTokenService.validateToken(accessToken);
        if (email == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE)
                .map(userResponseMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }


    // ===================== HÀM HỖ TRỢ =====================

    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        return cookie;
    }

    private Cookie createExpiredRefreshTokenCookie() {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}