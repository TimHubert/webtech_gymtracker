package com.htwberlin.webtech_projekt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    
    @NotBlank(message = "Benutzername ist erforderlich")
    @Size(min = 3, max = 50, message = "Benutzername muss zwischen 3 und 50 Zeichen lang sein")
    private String username;
    
    @NotBlank(message = "E-Mail ist erforderlich")
    @Email(message = "Ungültige E-Mail-Adresse")
    @Size(max = 100, message = "E-Mail darf maximal 100 Zeichen lang sein")
    private String email;
    
    @NotBlank(message = "Passwort ist erforderlich")
    @Size(min = 6, max = 255, message = "Passwort muss mindestens 6 Zeichen lang sein")
    private String password;
    
    // Konstruktoren
    public RegisterRequest() {}
    
    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Getter und Setter
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}