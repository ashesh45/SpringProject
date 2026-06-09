package com.bway.springdemo.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String password = user.getPassword();
        boolean isBcrypt = password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");

        if (!isBcrypt) {
            password = new BCryptPasswordEncoder().encode(password);
            user.setPassword(password);
            userRepository.save(user);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                password,
                new ArrayList<>()
        );
    }

	

}