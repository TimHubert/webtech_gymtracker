package com.htwberlin.webtech_projekt.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testDefaultConstructor() {
        // When
        User newUser = new User();

        // Then
        assertNotNull(newUser);
        assertNull(newUser.getId());
        assertNull(newUser.getUsername());
        assertNull(newUser.getEmail());
        assertNull(newUser.getPassword());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        String password = "hashedPassword123";

        // When
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        // Then
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    void testUsernameValidation() {
        // Given
        String validUsername = "validuser123";
        String emptyUsername = "";

        // When & Then
        user.setUsername(validUsername);
        assertEquals(validUsername, user.getUsername());

        user.setUsername(emptyUsername);
        assertEquals(emptyUsername, user.getUsername());
    }

    @Test
    void testEmailValidation() {
        // Given
        String validEmail = "test@example.com";
        String invalidEmail = "invalid-email";

        // When & Then
        user.setEmail(validEmail);
        assertEquals(validEmail, user.getEmail());

        user.setEmail(invalidEmail);
        assertEquals(invalidEmail, user.getEmail());
    }

    @Test
    void testPasswordHandling() {
        // Given
        String plainPassword = "mySecretPassword";
        String hashedPassword = "$2a$10$hashedPasswordExample";

        // When & Then
        user.setPassword(hashedPassword);
        assertEquals(hashedPassword, user.getPassword());
        assertNotEquals(plainPassword, user.getPassword());
    }

    @Test
    void testUserEquality() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("testuser");

        User user3 = new User();
        user3.setId(2L);
        user3.setUsername("differentuser");

        // When & Then
        // Note: Equality ist nicht implementiert, daher nur Identity Check
        assertNotEquals(user1, user2); // Verschiedene Objektinstanzen
        assertNotEquals(user1, user3);
        assertEquals(user1, user1); // Gleiche Instanz
    }

    @Test
    void testNullValues() {
        // When
        user.setUsername(null);
        user.setEmail(null);
        user.setPassword(null);

        // Then
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }
}
