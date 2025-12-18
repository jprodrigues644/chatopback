package com.op.chatopback.controller;

import com.op.chatopback.dto.*;
import com.op.chatopback.mapper.UserMapper;
import com.op.chatopback.service.AuthService;
import com.op.chatopback.util.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
/**
 * Controller for authentication-related endpoints.
 * <p>
 * Handles user registration, login, and retrieval of authenticated user details.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /** 
     * Registers a new user.
     *
     * @param request the registration request containing user details
     * @return the authentication response with user info and token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }
    /**
     * Authenticates a user and provides a JWT token.
     *
     * @param request the authentication request containing login credentials
     * @return the authentication response with user info and token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    /**
     * Retrieves details of the currently authenticated user.
     *
     * @param customUserDetails the authenticated user's details
     * @return the user DTO or a forbidden status if not authenticated
     */
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("User Not Authentified");
        }
        UserDto userDto = UserMapper.toDto(customUserDetails.getUser());
        return ResponseEntity.ok(userDto);
    }
}
