package com.smarhire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smarhire.dto.LoginRequest;
import com.smarhire.dto.LoginResponse;
import com.smarhire.dto.RegisterRequest;
import com.smarhire.exception.InvalidPasswordException;
import com.smarhire.exception.UserNotFoundException;
import com.smarhire.model.User;
import com.smarhire.repository.UserRepository;
import com.smarhire.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ MUST return LoginResponse
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("Invalid email address"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong password");
        }

        // ✅ Generate JWT with role
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        // ✅ Return token to controller
        return new LoginResponse(token);
    }
    public void register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 🔥 IMPORTANT
        user.setRole(request.getRole().toUpperCase());

        userRepository.save(user);
    }

}
