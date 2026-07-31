package com.hoangdo.quizapp.service;

import com.hoangdo.quizapp.dto.LoginRequest;
import com.hoangdo.quizapp.dto.RegisterRequest;
import com.hoangdo.quizapp.exception.ApiException;
import com.hoangdo.quizapp.model.User;
import com.hoangdo.quizapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder(); // use the real encoder, not a mock
        authService = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void register_hashesThePassword_neverStoresPlaintext() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("hoang");
        request.setPassword("correcthorsebattery");

        when(userRepository.existsByUsername("hoang")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = authService.register(request);

        assertThat(saved.getPasswordHash()).isNotEqualTo("correcthorsebattery");
        assertThat(passwordEncoder.matches("correcthorsebattery", saved.getPasswordHash())).isTrue();
    }

    @Test
    void register_rejectsDuplicateUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("hoang");
        request.setPassword("correcthorsebattery");

        when(userRepository.existsByUsername("hoang")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void login_succeedsWithCorrectPassword() {
        String hash = passwordEncoder.encode("correcthorsebattery");
        User existing = new User("hoang", hash);

        when(userRepository.findByUsername("hoang")).thenReturn(Optional.of(existing));

        LoginRequest request = new LoginRequest();
        request.setUsername("hoang");
        request.setPassword("correcthorsebattery");

        User result = authService.login(request);

        assertThat(result.getUsername()).isEqualTo("hoang");
    }

    @Test
    void login_rejectsWrongPassword() {
        String hash = passwordEncoder.encode("correcthorsebattery");
        User existing = new User("hoang", hash);

        when(userRepository.findByUsername("hoang")).thenReturn(Optional.of(existing));

        LoginRequest request = new LoginRequest();
        request.setUsername("hoang");
        request.setPassword("wrongpassword");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Invalid username or password");
    }

    @Test
    void login_rejectsUnknownUsername() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest();
        request.setUsername("ghost");
        request.setPassword("whatever");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Invalid username or password");
    }
}
