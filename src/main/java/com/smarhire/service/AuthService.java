package com.smarhire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smarhire.model.User;
import com.smarhire.repository.UserRepository;
import com.smarhire.exception.EmailAlreadyExistsException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        // 🔐 Encrypt password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
