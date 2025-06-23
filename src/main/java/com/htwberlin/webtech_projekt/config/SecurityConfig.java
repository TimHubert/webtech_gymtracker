package com.htwberlin.webtech_projekt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security Configuration for JWT-based REST API
 * 
 * Security Design:
 * - Stateless authentication using JWT tokens
 * - CSRF protection disabled (appropriate for stateless APIs)
 * - CORS enabled for cross-origin requests
 * - Session management disabled (stateless)
 * 
 * CSRF Rationale:
 * CSRF protection is intentionally disabled because:
 * 1. This is a stateless API using JWT tokens
 * 2. JWT tokens in Authorization headers are not automatically 
 *    sent by browsers, preventing CSRF attacks
 * 3. No session cookies are used that could be exploited
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF protection configuration for JWT-based stateless API
            // CSRF is not needed for stateless APIs using JWT tokens
            // as JWT tokens themselves provide protection against CSRF attacks
            .csrf(csrf -> {
                // For a pure stateless API with JWT authentication, CSRF protection
                // is unnecessary and actually interferes with API functionality
                // This is a security best practice for JWT-based APIs
                csrf.disable();
            })
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/", "/workout", "/OneWorkout").permitAll() // Demo endpoints
                .requestMatchers("/workouts/**", "/workoutsWithWeights/**", "/workout/**", "/OneWorkout/**", "/workoutWithWeights/**").authenticated() // Alle Workout endpoints
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}