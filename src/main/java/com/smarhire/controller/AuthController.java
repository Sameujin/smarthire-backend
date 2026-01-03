package com.smarhire.controller;

import org.springframework.web.bind.annotation.*;

import com.smarhire.model.User;
import com.smarhire.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }
}
