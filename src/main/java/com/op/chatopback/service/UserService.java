package com.op.chatopback.service;

import com.op.chatopback.dto.UserDto;
import com.op.chatopback.mapper.UserMapper;
import com.op.chatopback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling user operations.
 * <p>
 * This service provides functionality to retrieve user information.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
