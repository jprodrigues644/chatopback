package com.op.chatopback.service;
import com.op.chatopback.dto.*;
import com.op.chatopback.model.User;
import com.op.chatopback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication-related operations such as user registration and login.*
 * <p>
 * This service interacts with the UserRepository for user data management and uses
 * JWT for token generation.
 * </p>
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    /**
     * Registers a new user.
     *
     * @param registerRequest the registration request containing user details
     * @return the authentication response with user info and token
     */
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User Already Exist");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        AuthRequest authRequest = new AuthRequest(
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );

        return login(authRequest);
    }
    /**
     * Authenticates a user and provides a JWT token.
     *
     * @param authRequest the authentication request containing login credentials
     * @return the authentication response with user info and token
     */
    public AuthResponse login(AuthRequest authRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        ) ;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication); 
        return  new AuthResponse(jwt);
    }

}
