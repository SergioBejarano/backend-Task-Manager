package edu.eci.cvds.taskManager.controller;


import edu.eci.cvds.taskManager.dto.AuthRequest;
import edu.eci.cvds.taskManager.model.User;
<<<<<<< Updated upstream
=======
import edu.eci.cvds.taskManager.service.TaskService;
>>>>>>> Stashed changes
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
<<<<<<< Updated upstream
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
=======
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = loginUser(authRequest.getUsername(), authRequest.getPassword());

        if (user.isPresent()) {
>>>>>>> Stashed changes
            response.put("message", "Login successful!");
            response.put("user", user.get());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
<<<<<<< Updated upstream
=======


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        try {
            TaskService.registerUser(authRequest.getUsername(), authRequest.getPassword());
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
>>>>>>> Stashed changes
}
