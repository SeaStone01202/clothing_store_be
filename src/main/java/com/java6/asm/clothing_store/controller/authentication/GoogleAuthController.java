package com.java6.asm.clothing_store.controller.authentication;

import com.java6.asm.clothing_store.service.authentication.OAuthGoogleUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth/google")
@RequiredArgsConstructor
public class GoogleAuthController {

    @Value("${google.redirect}")
    private String redirect;

    private final OAuthGoogleUserService oAuthGoogleUserService;

    @GetMapping("/success")
    public void googleLoginSuccess(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response) throws IOException {
        oAuthGoogleUserService.authenticateAndGenerateTokens(oauth2User, null, response);
        response.sendRedirect(redirect);
    }
}
