package com.java6.asm.clothing_store.service.authentication.impl;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import com.java6.asm.clothing_store.dto.mapper.GoogleUserMapper;
import com.java6.asm.clothing_store.dto.mapper.UserRequestMapper;
import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.dto.response.AuthResponse;
import com.java6.asm.clothing_store.dto.response.GoogleUserRegisterResponse;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.authentication.DeviceManagementService;
import com.java6.asm.clothing_store.service.authentication.OAuthGoogleUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OAuthGoogleUserServiceImpl implements OAuthGoogleUserService {

    private final UserRepository userRepository;

    private final GoogleUserMapper googleUserMapper;

    private final DeviceManagementService deviceManagementService;

    private final UserRequestMapper userRequestMapper;

    private final JwtAccessRefreshTokenService jwtAccessTokenService;

    @Override
    public AuthResponse authenticateAndGenerateTokens(OAuth2User oauth2User,String deviceId, HttpServletResponse response) {

        UserRequest request = changeUserByOAuth2User(oauth2User);

        User user = userRepository.findByEmailAndStatus(request.getEmail(), StatusEnum.ACTIVE).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String refreshToken = deviceManagementService.getOrGenerateRefreshToken(request.getEmail(), deviceId);

        String accessToken = jwtAccessTokenService.generateToken(refreshToken, user.getRole());

        Cookie refreshTokenCookie = createRefreshTokenCookie(refreshToken);

        response.addCookie(refreshTokenCookie);

        return AuthResponse.builder().accessToken(accessToken).build();
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        return null;
    }

    @Override
    public String logout(String refreshToken, HttpServletResponse response) {
        return "";
    }

    @Override
    public UserResponse getUser(String authorizationHeader) {
        return null;
    }

    @Override
    public UserRequest changeUserByOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        Optional<User> existingUser = userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFullname(name);
            user.setImage(picture);
            userRepository.save(user);
            return userRequestMapper.toResponse(user);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setFullname(name);
            user.setImage(picture);
            user.setStatus(StatusEnum.ACTIVE);
            user.setRole(RoleEnum.CUSTOMER);
            user.setType(TypeAccountEnum.GOOGLE);
            userRepository.save(user);
            return userRequestMapper.toResponse(user);
        }
    }


    /**
     * ✅ Tìm user theo username (tài khoản hệ thống)
     */
    @Override
    public GoogleUserRegisterResponse getUserByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE).map(googleUserMapper::toResponse).orElseThrow();
    }


    /**
     * ✅ Tìm hoặc tạo user mới khi đăng nhập bằng OAuth (Google, Zalo...)
     */
    @Override
    public GoogleUserRegisterResponse findOrCreateUser(String email, String name, String image, TypeAccountEnum type) {
        return userRepository.findByEmailAndStatus(email, StatusEnum.ACTIVE)
                .map(googleUserMapper::toResponse)
                .orElseGet(() -> {
                    GoogleUserRegisterResponse newUser = createUser(email, name, image, type);
                    return newUser;
                });
    }


    /**
     * ✅ Tạo user mới
     */
    public GoogleUserRegisterResponse createUser(String email, String name, String image, TypeAccountEnum type) {
        User newUser = User.builder()
                .fullname(name)
                .email(email)
                .image(image)
                .type(type)
                .role(RoleEnum.CUSTOMER)
                .status(StatusEnum.ACTIVE)
                .build();

        userRepository.save(newUser);
        return googleUserMapper.toResponse(newUser);
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