package com.smarhire.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smarhire.dto.LoginRequest;
import com.smarhire.dto.LoginResponse;
import com.smarhire.dto.RegisterRequest;
import com.smarhire.exception.EmailAlreadyExistsException;
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

    // =========================
    // ✅ REGISTER USER
    // =========================
    public void register(RegisterRequest request) {

        // 🔒 Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // 🔐 Encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ Set role safely
        String role = request.getRole();
        if (role == null || role.isBlank()) {
            role = "JOB_SEEKER"; // default role
        }
        user.setRole(role.toUpperCase());

        userRepository.save(user);
    }

    // =========================
    // ✅ LOGIN USER
    // =========================
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("Invalid email address"));

        // 🔑 Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong password");
        }

        // 🔐 Generate JWT with role
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new LoginResponse(token);
    }
}
