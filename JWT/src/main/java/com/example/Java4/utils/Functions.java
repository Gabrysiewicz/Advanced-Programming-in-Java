package com.example.Java4.utils;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class Functions {
    public static String extractJwtToken(JwtAuthenticationToken jwtAuthenticationToken) {
        Object principal = jwtAuthenticationToken.getPrincipal();

        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            return jwt.getTokenValue();
        }
        return null;
    }
}
