package com.java6.asm.clothing_store.service.authentication.impl;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.service.authentication.AccessTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class JwtAccessRefreshTokenService implements AccessTokenService {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    /**
     * 🔑 Tạo Access Token bằng RSA Private Key
     * - Thời gian sống: 5 phút
     * - Chứa thông tin user & role
     *
     * @param username Tên user để gán vào JWT
     * @return Chuỗi Access Token đã ký
     */
    @Override
    public String generateToken(String email, RoleEnum role) {
        Instant now = Instant.now();
        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                JwtClaimsSet.builder()
                        .subject(email)
                        .issuedAt(now)
                        .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                        .claim("scope", role)
                        .build()
        )).getTokenValue();
    }

    @Override
    public String validateToken(String token) {
        try {
            Jwt decodedToken = jwtDecoder.decode(token);
            return decodedToken.getSubject();
        } catch (JwtException e) {
            throw new AppException(ErrorCode.ACCESS_TOKEN_INVALID);
        }
    }

}
