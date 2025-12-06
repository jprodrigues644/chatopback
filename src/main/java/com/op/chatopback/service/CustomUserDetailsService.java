package com.op.chatopback.service;

import com.op.chatopback.model.User;
import com.op.chatopback.repository.UserRepository;
import com.op.chatopback.util.CustomUserDetails;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.stereotype.Service;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
      // On peut pas Utiliser Juste User car ça confond avec le  User du Modek
        System.out.println( " User : " + user.getEmail());
        return new CustomUserDetails(user);
    }


}
