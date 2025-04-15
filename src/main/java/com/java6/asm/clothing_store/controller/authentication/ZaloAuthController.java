package com.java6.asm.clothing_store.controller.authentication;

import com.java6.asm.clothing_store.service.authentication.impl.JwtAccessRefreshTokenService;
import com.java6.asm.clothing_store.service.authentication.impl.JwtRefreshRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/zalo")
@RequiredArgsConstructor
public class ZaloAuthController {

    private final JwtAccessRefreshTokenService jwtAccessTokenService;
    private final JwtRefreshRefreshTokenService jwtRefreshTokenService;

//    @GetMapping("/callback")
//    public ResponseEntity<ApiResponse<AuthResponse>> zaloLoginSuccess(@RequestParam("code") String code) {
//
//        ZaloUserRegisterResponse user = authService.authenticateZaloUser(code);
//
//        String accessToken = jwtAccessTokenService.generateToken(user.getName());
//        String refreshToken = jwtRefreshTokenService.generateToken(user.getName());
//
//        return ResponseEntity.ok(ApiResponse.success(new AuthResponse(accessToken, refreshToken)));
//    }
}
