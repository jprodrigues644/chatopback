package com.op.chatopback.service;

import com.op.chatopback.model.User;
import com.op.chatopback.repository.UserRepository;
import com.op.chatopback.util.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService to load user-specific data.
 * <p>
 * This service retrieves user details from the database using the UserRepository.
 * It is used by Spring Security for authentication and authorization processes.
 * </p>
 */
@Service

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Loads a user by their username (email).
     *
     * @param email the email of the user to load
     * @return UserDetails containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        System.out.println( " User : " + user.getEmail());
        return new CustomUserDetails(user);
    }


}
