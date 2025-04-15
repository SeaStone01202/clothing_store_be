package com.java6.asm.clothing_store.service.authentication;

public interface DeviceManagementService {

    String getOrGenerateRefreshToken(String email, String deviceId);

    boolean removeRefreshToken(String refreshToken);
}
