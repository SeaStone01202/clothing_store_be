package com.java6.asm.clothing_store.service.authentication.impl;

import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.service.authentication.DeviceManagementService;
import com.java6.asm.clothing_store.service.authentication.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DeviceManagementServiceImpl implements DeviceManagementService {

    private final RefreshTokenService jwtRefreshTokenService;

    @Override
    public String getOrGenerateRefreshToken(String email, String deviceId) {
        int deviceCount = jwtRefreshTokenService.countDevices(email);

        if (deviceCount >= 3 && !jwtRefreshTokenService.existsTokenForDevice(email, deviceId)) {
            throw new AppException(ErrorCode.TOO_MANY_DEVICES);
        }

        String refreshToken = jwtRefreshTokenService.getTokenForDevice(email, deviceId);
        return (refreshToken != null) ? refreshToken : jwtRefreshTokenService.generateToken(email, deviceId);
    }

    @Override
    public boolean removeRefreshToken(String refreshToken) {
        return jwtRefreshTokenService.deleteToken(refreshToken);
    }
}

