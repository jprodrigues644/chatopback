package com.op.chatopback.service;


import com.op.chatopback.dto.*;
import com.op.chatopback.model.User;
import com.op.chatopback.repository.UserRepository;
import com.op.chatopback.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    //User Sign UP
    public RegisterResponse registerUser (RegisterRequest registerRequest) {
         if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
             throw new RuntimeException("User Already Exist");
         }

         User user = new User();
         user.setEmail(registerRequest.getEmail());
         user.setName(registerRequest.getName());
         user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);

        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setRegistrationDate(saved.getCreatedAt());
        response.setConfirmationMessage("User registered successfully!");// Message à Changer

        return response;

    }

   public AuthResponse login(AuthRequest authRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        ) ;

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // We generate the Token jwt
        String jwt = jwtUtils.generateJwtToken(authentication);
        // generateJwtToken(auth)
        return  new AuthResponse(jwt);
    }




}
