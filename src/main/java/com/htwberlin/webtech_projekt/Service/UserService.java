package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Repository.UserRepository;
import com.htwberlin.webtech_projekt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public User getCurrentUserFromToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Kein gültiger Authorization-Header gefunden");
        }
        
        String token = authorizationHeader.substring(7);
        
        if (!jwtUtil.validateJwtToken(token)) {
            throw new RuntimeException("Ungültiger Token");
        }
        
        String username = jwtUtil.getUserNameFromJwtToken(token);
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    }
}