package com.op.chatopback.service;


import com.op.chatopback.dto.AuthRequest;
import com.op.chatopback.dto.AuthResponse;
import com.op.chatopback.dto.RegisterRequest;
import com.op.chatopback.dto.UserDto;
import com.op.chatopback.model.User;
import com.op.chatopback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    //User Sign UP
    private UserDto registerUser (RegisterRequest registerRequest) {
         if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
             throw new RuntimeException("User Already Exist");
         }

         User user = new User();
         user.setEmail(registerRequest.getEmail());
         user.setName(registerRequest.getName());
         user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());

        return userDto;

    }

    private AuthResponse login(AuthRequest authRequest){
        return  null;
    }




}
