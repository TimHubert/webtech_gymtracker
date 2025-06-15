package com.htwberlin.webtech_projekt.config;

import com.htwberlin.webtech_projekt.util.JwtUtil;
import com.htwberlin.webtech_projekt.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        System.out.println("JWT Filter: Processing request to " + requestPath);

        // Skip authentication for auth endpoints
        if (requestPath.startsWith("/api/auth/")) {
            System.out.println("JWT Filter: Skipping auth endpoint " + requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("JWT Filter: Authorization header = " + (authorizationHeader != null ? "Present" : "Missing"));

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("JWT Filter: Token extracted, length = " + jwt.length());
            
            try {
                username = jwtUtil.getUserNameFromJwtToken(jwt);
                System.out.println("JWT Filter: Username from token = " + username);
            } catch (Exception e) {
                System.err.println("JWT Filter: Error extracting username from token: " + e.getMessage());
            }
        } else {
            System.out.println("JWT Filter: No Bearer token found");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                com.htwberlin.webtech_projekt.Model.User user = userRepository.findByUsername(username).orElse(null);
                
                if (user != null && jwtUtil.validateJwtToken(jwt)) {
                    System.out.println("JWT Filter: Token valid for user " + username);
                    
                    UserDetails userDetails = User.builder()
                            .username(user.getUsername())
                            .password("") // Password not needed for JWT authentication
                            .authorities(new ArrayList<>())
                            .build();

                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    
                    // Store user in request for easy access
                    request.setAttribute("currentUser", user);
                    System.out.println("JWT Filter: Authentication set for " + username);
                } else {
                    System.err.println("JWT Filter: Invalid token or user not found");
                }
            } catch (Exception e) {
                System.err.println("JWT Filter: Authentication error: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}