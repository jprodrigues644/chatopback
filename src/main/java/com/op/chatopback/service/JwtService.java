package com.op.chatopback.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Service for handling JWT (JSON Web Token) operations.
 * <p>
 * This service provides functionality to generate, validate, and extract information from JWT tokens.
 * </p>
 */
@Service
@Getter
@Setter
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    /** Generates a JWT token for the given authentication.
     *
     * @param authentication the authentication object containing user details
     * @return the generated JWT token
     */
    public String generateToken(Authentication authentication){
        UserDetails user =  (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(getKey())
                .compact();
    }
    /** Retrieves the signing key for JWT operations.
     *
     * @return the signing key
     */
    public Key getKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(KeyBytes);

    }

    /** Extracts the username from a JWT token.
     *
     * @param jwt the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }
    /** Validates a JWT token against user details.
     *
     * @param jwt         the JWT token
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    /** Checks if a JWT token is expired.
     *
     * @param jwt the JWT token
     * @return true if the token is expired, false otherwise
     */
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
