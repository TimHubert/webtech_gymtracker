package com.htwberlin.webtech_projekt.Controller;

import com.htwberlin.webtech_projekt.Model.User;
import com.htwberlin.webtech_projekt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.usernameAlreadyExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("Benutzername bereits vergeben.");
        }
        User registeredUser = userService.registerUser(user);

        User safeUser = new User();
        safeUser.setUsername(registeredUser.getUsername());
        return ResponseEntity.ok(safeUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticatedUser.isPresent()) {
            User safeUser = new User();
            safeUser.setUsername(authenticatedUser.get().getUsername());
            return ResponseEntity.ok(safeUser);
        } else {
            return ResponseEntity.status(401).body("Ungültiger Benutzername oder Passwort.");
        }
    }
}
