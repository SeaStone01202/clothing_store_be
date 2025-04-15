package com.java6.asm.clothing_store.yc3;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.mapper.UserResponseMapper;
import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.dto.response.AuthResponse;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.authentication.AccessTokenService;
import com.java6.asm.clothing_store.service.authentication.DeviceManagementService;
import com.java6.asm.clothing_store.service.authentication.RefreshTokenService;
import com.java6.asm.clothing_store.service.authentication.impl.OAuthSystemUserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OAuthSystemUserServiceImplTest {

    @InjectMocks
    private OAuthSystemUserServiceImpl service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private AccessTokenService jwtAccessTokenService;

    @Mock
    private RefreshTokenService jwtRefreshTokenService;

    @Mock
    private DeviceManagementService deviceManagementService;

    @Mock
    private HttpServletResponse response;

    @Test
    void testLoginSuccess_TC01() {

        UserRequest request = UserRequest.builder()
                .email("user@example.com")
                .password("correctPass")
                .deviceId("device123")
                .build();

        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("hashedPassword");

        when(userRepository.findByEmailAndStatus("user@example.com", StatusEnum.ACTIVE))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches(eq("correctPass"), eq("hashedPassword")))
                .thenReturn(true);

        when(deviceManagementService.getOrGenerateRefreshToken(any(), any()))
                .thenReturn("fakeRefreshToken");

        when(jwtAccessTokenService.generateToken(any(), any())).thenReturn("fakeAccessToken");

        AuthResponse result = service.authenticateAndGenerateTokens(request, response);

        assertEquals("fakeAccessToken", result.getAccessToken());
    }

    @Test
    void testLoginFailureForUserNotExited_TC02() {

        UserRequest request = UserRequest.builder()
                .email("nonexist@example.com")
                .password("pass")
                .deviceId("device123")
                .build();

        when(userRepository.findByEmailAndStatus("nonexist@example.com", StatusEnum.ACTIVE))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            service.authenticateAndGenerateTokens(request, response);
        });

        assertEquals(ErrorCode.USER_NOT_EXISTED, exception.getErrorCode());
    }

@Test
    void testLoginFailureForPasswordIncorrect_TC03() {

        UserRequest request = UserRequest.builder()
                .email("user@example.com")
                .password("wrongPass")
                .deviceId("device123")
                .build();

        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("hashedPassword");

        when(userRepository.findByEmailAndStatus("user@example.com", StatusEnum.ACTIVE))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("wrongPass", "hashedPassword"))
                .thenReturn(false);

        AppException exception = assertThrows(AppException.class, () -> {
            service.authenticateAndGenerateTokens(request, response);
        });

        assertEquals(ErrorCode.INVALID_CREDENTIALS, exception.getErrorCode());
    }

    @Test
    void testLoginFailureTooManyDevices_TC04() {

        UserRequest request = UserRequest.builder()
                .email("user@example.com")
                .password("correctPass")
                .deviceId("device4")
                .build();

        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("hashedPassword");

        when(userRepository.findByEmailAndStatus("user@example.com", StatusEnum.ACTIVE))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("correctPass", "hashedPassword"))
                .thenReturn(true);

        when(deviceManagementService.getOrGenerateRefreshToken("user@example.com", "device4"))
                .thenThrow(new AppException(ErrorCode.TOO_MANY_DEVICES));

        AppException exception = assertThrows(AppException.class, () -> {
            service.authenticateAndGenerateTokens(request, response);
        });

        assertEquals(ErrorCode.TOO_MANY_DEVICES, exception.getErrorCode());
    }


    @Test
    void testLoginSuccessWithNullDeviceId_TC06() {

        UserRequest request = UserRequest.builder()
                .email("user@example.com")
                .password("correctPass")
                .deviceId(null)
                .build();

        User mockUser = new User();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("hashedPassword");
        mockUser.setRole(RoleEnum.CUSTOMER);


        when(userRepository.findByEmailAndStatus("user@example.com", StatusEnum.ACTIVE))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("correctPass", "hashedPassword"))
                .thenReturn(true);

        when(deviceManagementService.getOrGenerateRefreshToken(mockUser.getEmail(), null))
                .thenReturn("fakeRefreshToken");

        when(jwtAccessTokenService.generateToken(mockUser.getEmail(), mockUser.getRole())).thenReturn("fakeAccessToken");

        AuthResponse result = service.authenticateAndGenerateTokens(request, response);

        assertEquals("fakeAccessToken", result.getAccessToken());

        verify(deviceManagementService).getOrGenerateRefreshToken(mockUser.getEmail(), null);
    }

}