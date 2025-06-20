package com.htwberlin.webtech_projekt.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Setze die private Felder für Tests
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "myTestSecretKey123456789012345678901234567890123456789012345678901234567890");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 86400000); // 24 Stunden
    }

    @Test
    void testGenerateJwtToken() {
        // Given
        String username = "testuser";

        // When
        String token = jwtUtil.generateJwtToken(username);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void testGetUserNameFromJwtToken() {
        // Given
        String username = "testuser";
        String token = jwtUtil.generateJwtToken(username);

        // When
        String extractedUsername = jwtUtil.getUserNameFromJwtToken(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        // Given
        String username = "testuser";
        String token = jwtUtil.generateJwtToken(username);

        // When
        boolean isValid = jwtUtil.validateJwtToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        // Given
        String invalidToken = "invalid.token.here";

        // When
        boolean isValid = jwtUtil.validateJwtToken(invalidToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_EmptyToken() {
        // Given
        String emptyToken = "";

        // When
        boolean isValid = jwtUtil.validateJwtToken(emptyToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_NullToken() {
        // Given
        String nullToken = null;

        // When
        boolean isValid = jwtUtil.validateJwtToken(nullToken);

        // Then
        assertFalse(isValid);
    }
}