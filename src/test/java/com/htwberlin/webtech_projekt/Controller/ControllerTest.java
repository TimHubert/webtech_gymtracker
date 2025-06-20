package com.htwberlin.webtech_projekt.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testIndexEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Greetings from Spring Boot!"));
    }

    @Test
    void testWorkoutEndpoint() throws Exception {
        mockMvc.perform(get("/workout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Push"))
                .andExpect(jsonPath("$.show").value(true))
                .andExpect(jsonPath("$.exercise").isArray());
    }

    @Test
    void testOneWorkoutEndpoint() throws Exception {
        mockMvc.perform(get("/OneWorkout"))
                .andExpect(status().isOk());
    }
}
