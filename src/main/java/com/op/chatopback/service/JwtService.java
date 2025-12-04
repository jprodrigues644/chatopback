package com.op.chatopback.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;



@Service
@Getter
@Setter
public class JwtService {

    private String secretKey="c2VjcmV0S2V5Zm9ySmF3VFRva2VuZ2VuZXJhdGlvblNlY3JldEtleQ==";


    public String generateToken(Authentication authentication){
        UserDetails user =  (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(getKey())
                .compact();
    }

    public Key getKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(KeyBytes);

    }




    public String extractUsername(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }




    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
