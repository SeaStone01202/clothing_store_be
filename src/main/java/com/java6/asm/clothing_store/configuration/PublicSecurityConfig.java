package com.java6.asm.clothing_store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.stream.Stream;

@Configuration
@Order(1)
public class PublicSecurityConfig {

    // Nhóm endpoint public cho ProductController
    private static final String[] productUrls = {
            "/product",
            "/product/list",
            "/product/category/**"
    };

    // Nhóm endpoint public cho CategoryController
    private static final String[] categoryUrls = {
            "/category/status",
            "/category"
    };

    // Nhóm endpoint public cho OrderController (nếu có)
    private static final String[] orderUrls = {
            // Ví dụ: "/orders/public/list"
    };

    // Nhóm endpoint public chung (auth, swagger, ...)
    private static final String[] commonUrls = {
            "/auth/system/refresh",
            "/auth/system/login",
            "/user/register",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/user/forgot_password",
    };

    // Gộp tất cả thành publicUrls
    private String[] getPublicUrls() {
        return Stream.of(commonUrls, productUrls, categoryUrls, orderUrls)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }

    @Bean
    public SecurityFilterChain refreshSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(getPublicUrls())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults());
        return http.build();
    }
}