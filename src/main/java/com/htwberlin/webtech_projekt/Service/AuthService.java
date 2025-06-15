package com.htwberlin.webtech_projekt.Service;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Repository.UserRepository;
import com.htwberlin.webtech_projekt.dto.AuthResponse;
import com.htwberlin.webtech_projekt.dto.LoginRequest;
import com.htwberlin.webtech_projekt.dto.RegisterRequest;
import com.htwberlin.webtech_projekt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse registerUser(RegisterRequest registerRequest) {
        // Prüfen ob Benutzer bereits existiert
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Benutzername bereits vergeben");
        }
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("E-Mail bereits registriert");
        }

        // Neuen Benutzer erstellen
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);

        // JWT Token generieren
        String token = jwtUtil.generateJwtToken(savedUser.getUsername());

        return new AuthResponse(token, savedUser.getUsername(), savedUser.getEmail(), savedUser.getId());
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        // Benutzer suchen
        User user = userRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RuntimeException("Ungültige Anmeldedaten"));

        // Passwort prüfen
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Ungültige Anmeldedaten");
        }

        // JWT Token generieren
        String token = jwtUtil.generateJwtToken(user.getUsername());

        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getId());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    }
    
    public User updateUserProfile(String currentUsername, User profileData) {
        // Aktuellen Benutzer laden
        User existingUser = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
        
        // Prüfen ob neuer Benutzername bereits vergeben ist (falls geändert)
        if (!existingUser.getUsername().equals(profileData.getUsername()) && 
            userRepository.existsByUsername(profileData.getUsername())) {
            throw new RuntimeException("Benutzername bereits vergeben");
        }
        
        // Prüfen ob neue E-Mail bereits vergeben ist (falls geändert)
        if (!existingUser.getEmail().equals(profileData.getEmail()) && 
            userRepository.existsByEmail(profileData.getEmail())) {
            throw new RuntimeException("E-Mail bereits registriert");
        }
        
        // Profildaten aktualisieren (nur die verfügbaren Felder)
        existingUser.setUsername(profileData.getUsername());
        existingUser.setEmail(profileData.getEmail());
        
        return userRepository.save(existingUser);
    }
}