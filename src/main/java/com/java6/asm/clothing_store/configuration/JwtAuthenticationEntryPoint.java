package com.java6.asm.clothing_store.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error("Authentication error for request [{} {}]: {}",
                request.getMethod(), request.getRequestURI(), authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Xác định nguyên nhân lỗi
        ErrorCode errorCode = determineErrorCode(authException);
        ApiResponse<String> errorResponse = ApiResponse.error(
                new AppException(errorCode)
        );

        try {
            objectMapper.writeValue(response.getOutputStream(), errorResponse);
        } catch (Exception e) {
            logger.error("Error writing error response: {}", e.getMessage(), e);
            response.getOutputStream().write("{\"status\": 500, \"message\": \"Internal server error\", \"data\": null}".getBytes());
        }
    }

    private ErrorCode determineErrorCode(AuthenticationException authException) {
        String message = authException.getMessage().toLowerCase();
        if (message.contains("expired")) {
            return ErrorCode.TOKEN_EXPIRED;
        } else if (message.contains("invalid")) {
            return ErrorCode.TOKEN_INVALID;
        } else if (message.contains("jwt") || message.contains("token")) {
            return ErrorCode.TOKEN_INVALID;
        } else {
            return ErrorCode.UNAUTHENTICATED;
        }
    }
}