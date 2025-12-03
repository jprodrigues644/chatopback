package com.op.chatopback.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public String generateJwtToken(Authentication authentication) {
        return null;    }

    //generateJwtToken(auth), validateToken, getEmailFromToken
}
