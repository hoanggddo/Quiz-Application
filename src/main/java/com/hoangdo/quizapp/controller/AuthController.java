package com.hoangdo.quizapp.controller;

import com.hoangdo.quizapp.dto.LoginRequest;
import com.hoangdo.quizapp.dto.RegisterRequest;
import com.hoangdo.quizapp.model.User;
import com.hoangdo.quizapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Account created", "username", user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        return ResponseEntity.ok(Map.of("message", "Login successful", "username", user.getUsername()));
    }
}
