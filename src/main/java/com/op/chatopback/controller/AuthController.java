package com.op.chatopback.controller;

import com.op.chatopback.dto.*;
import com.op.chatopback.mapper.UserMapper;
import com.op.chatopback.model.User;
import com.op.chatopback.service.AuthService;
import com.op.chatopback.util.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    //Methode for User Creation will give the full information about the User Created
    private final AuthService  authService;
    @PostMapping("/register")
    public ResponseEntity <RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    // Methode for Connexion , will give the Jwt toker
    @PostMapping("/login")
    public ResponseEntity <AuthResponse> login (@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    // Give the current user by Using the AuthenticationPrincipal
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println(customUserDetails);
        if (customUserDetails == null) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("User Not Authentified");
        }
        UserDto userDto =   UserMapper.toDto(customUserDetails.getUser());
        return ResponseEntity.ok( userDto);
    }

}
