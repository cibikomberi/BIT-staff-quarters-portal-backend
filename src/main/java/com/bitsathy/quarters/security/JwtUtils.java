package com.bitsathy.quarters.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {

    public static Long getUserIdFromToken(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return (Long) jwt.getClaims().get("id"); 
    }
}

