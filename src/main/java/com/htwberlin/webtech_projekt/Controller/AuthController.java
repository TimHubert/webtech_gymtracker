package com.htwberlin.webtech_projekt.Controller;

import com.htwberlin.webtech_projekt.Service.AuthService;
import com.htwberlin.webtech_projekt.dto.AuthResponse;
import com.htwberlin.webtech_projekt.dto.LoginRequest;
import com.htwberlin.webtech_projekt.dto.RegisterRequest;
import com.htwberlin.webtech_projekt.util.JwtUtil;
import com.htwberlin.webtech_projekt.Model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.registerUser(registerRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.loginUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            // Token aus dem Header extrahieren (Bearer prefix entfernen)
            String jwtToken = token.replace("Bearer ", "");
            
            if (!jwtUtil.validateJwtToken(jwtToken)) {
                return ResponseEntity.status(401).body("Ungültiger Token");
            }
            
            String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
            User user = authService.getUserByUsername(username);
            
            return ResponseEntity.ok(new AuthResponse(null, user.getUsername(), user.getEmail(), user.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Authentifizierung fehlgeschlagen");
        }
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token, @RequestBody User profileData) {
        try {
            // Token aus dem Header extrahieren (Bearer prefix entfernen)
            String jwtToken = token.replace("Bearer ", "");
            
            if (!jwtUtil.validateJwtToken(jwtToken)) {
                return ResponseEntity.status(401).body("Ungültiger Token");
            }
            
            String username = jwtUtil.getUserNameFromJwtToken(jwtToken);
            User updatedUser = authService.updateUserProfile(username, profileData);
            
            return ResponseEntity.ok(new AuthResponse(null, updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Fehler beim Aktualisieren des Profils");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Logout ist normalerweise frontend-seitig (Token löschen)
        // Hier können Sie zusätzliche Backend-Logik implementieren falls nötig
        return ResponseEntity.ok("Logout erfolgreich");
    }
}