package com.htwberlin.webtech_projekt.Controller;

import com.htwberlin.webtech_projekt.Service.AuthService;
import com.htwberlin.webtech_projekt.dto.AuthResponse;
import com.htwberlin.webtech_projekt.dto.LoginRequest;
import com.htwberlin.webtech_projekt.dto.RegisterRequest;
import com.htwberlin.webtech_projekt.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        authResponse = new AuthResponse("jwt-token", "testuser", "test@example.com", 1L);
    }

    @Test
    void testRegisterSuccess() {
        // Given
        when(authService.registerUser(any(RegisterRequest.class))).thenReturn(authResponse);

        // When
        ResponseEntity<?> response = authController.register(registerRequest);

        // Then
        assertEquals(200, response.getStatusCode().value());
        AuthResponse body = (AuthResponse) response.getBody();
        assertNotNull(body);
        assertEquals("jwt-token", body.getToken());
        assertEquals("testuser", body.getUsername());
        verify(authService).registerUser(registerRequest);
    }

    @Test
    void testRegisterFailure() {
        // Given
        when(authService.registerUser(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Benutzername bereits vergeben"));

        // When
        ResponseEntity<?> response = authController.register(registerRequest);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Benutzername bereits vergeben", response.getBody());
    }

    @Test
    void testLoginSuccess() {
        // Given
        when(authService.loginUser(any(LoginRequest.class))).thenReturn(authResponse);

        // When
        ResponseEntity<?> response = authController.login(loginRequest);

        // Then
        assertEquals(200, response.getStatusCode().value());
        AuthResponse body = (AuthResponse) response.getBody();
        assertNotNull(body);
        assertEquals("jwt-token", body.getToken());
        assertEquals("testuser", body.getUsername());
        verify(authService).loginUser(loginRequest);
    }

    @Test
    void testLoginFailure() {
        // Given
        when(authService.loginUser(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Ungültige Anmeldedaten"));

        // When
        ResponseEntity<?> response = authController.login(loginRequest);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Ungültige Anmeldedaten", response.getBody());
    }

    @Test
    void testLogout() {
        // When
        ResponseEntity<?> response = authController.logout();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}
