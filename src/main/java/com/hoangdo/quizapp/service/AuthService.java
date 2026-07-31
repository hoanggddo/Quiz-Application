package com.hoangdo.quizapp.service;

import com.hoangdo.quizapp.dto.LoginRequest;
import com.hoangdo.quizapp.dto.RegisterRequest;
import com.hoangdo.quizapp.exception.ApiException;
import com.hoangdo.quizapp.model.User;
import com.hoangdo.quizapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException("Username already exists", HttpStatus.CONFLICT);
        }

        String hash = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), hash);
        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ApiException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        return user;
    }
}
