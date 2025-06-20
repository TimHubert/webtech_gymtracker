package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Repository.UserRepository;
import com.htwberlin.webtech_projekt.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
    }

    @Test
    void testGetCurrentUserFromToken_Success() {
        // Given
        String authHeader = "Bearer valid-token";
        when(jwtUtil.validateJwtToken("valid-token")).thenReturn(true);
        when(jwtUtil.getUserNameFromJwtToken("valid-token")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // When
        User result = userService.getCurrentUserFromToken(authHeader);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testGetCurrentUserFromToken_NoBearer() {
        // Given
        String authHeader = "invalid-header";

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.getCurrentUserFromToken(authHeader));
        assertEquals("Kein gültiger Authorization-Header gefunden", exception.getMessage());
    }

    @Test
    void testGetCurrentUserFromToken_NullHeader() {
        // Given
        String authHeader = null;

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.getCurrentUserFromToken(authHeader));
        assertEquals("Kein gültiger Authorization-Header gefunden", exception.getMessage());
    }

    @Test
    void testGetCurrentUserFromToken_InvalidToken() {
        // Given
        String authHeader = "Bearer invalid-token";
        when(jwtUtil.validateJwtToken("invalid-token")).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.getCurrentUserFromToken(authHeader));
        assertEquals("Ungültiger Token", exception.getMessage());
    }

    @Test
    void testGetCurrentUserFromToken_UserNotFound() {
        // Given
        String authHeader = "Bearer valid-token";
        when(jwtUtil.validateJwtToken("valid-token")).thenReturn(true);
        when(jwtUtil.getUserNameFromJwtToken("valid-token")).thenReturn("nonexistent");
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.getCurrentUserFromToken(authHeader));
        assertEquals("Benutzer nicht gefunden", exception.getMessage());
    }
}