package edu.eci.cvds.taskManager.controller;


import edu.eci.cvds.taskManager.dto.AuthRequest;
import edu.eci.cvds.taskManager.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static edu.eci.cvds.taskManager.service.TaskService.loginUser;
import static edu.eci.cvds.taskManager.service.TaskService.registerUser;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = loginUser(authRequest.getUsername(), authRequest.getPassword());

        if (user.isPresent()) {
            response.put("message", "Login successful!");
            response.put("user", user.get()); // O incluye un token o ID de sesión
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = registerUser(authRequest.getUsername(), authRequest.getPassword());

        if (user.isPresent()) {
            response.put("message", "Registration successful!");
            response.put("user", user.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Registration failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
