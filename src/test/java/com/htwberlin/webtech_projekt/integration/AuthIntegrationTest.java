package com.htwberlin.webtech_projekt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htwberlin.webtech_projekt.dto.LoginRequest;
import com.htwberlin.webtech_projekt.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPublicEndpointsAccessible() throws Exception {
        // Test root endpoint
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        // Test workout endpoint
        mockMvc.perform(get("/workout"))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterEndpoint() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("integrationtestuser");
        registerRequest.setEmail("integration@test.com");
        registerRequest.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("integrationtestuser"));
    }

    @Test
    void testRegisterWithInvalidData() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        // Leere Daten für Validation Test
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }
}
