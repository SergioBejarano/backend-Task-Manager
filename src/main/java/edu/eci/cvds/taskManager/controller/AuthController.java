package edu.eci.cvds.taskManager.controller;


import edu.eci.cvds.taskManager.dto.AuthRequest;
import edu.eci.cvds.taskManager.model.User;
import edu.eci.cvds.taskManager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class  AuthController {

    private final AuthenticationManager authenticationManager;
    private final TaskService taskService;


    /**
     * Constructor to initialize AuthController with required dependencies.
     *
     * @param authenticationManager the authentication manager for handling login authentication
     * @param taskService the task service providing login and registration functionalities
     */
    public AuthController(AuthenticationManager authenticationManager, TaskService taskService) {
        this.authenticationManager = authenticationManager;
        this.taskService = taskService;
    }

    /**
     * Handles user login.
     *
     * <p>This method authenticates user credentials and returns a response with a success message,
     * user details, and role information if login is successful.</p>
     *
     * @param authRequest the authentication request containing username and password
     * @return a ResponseEntity containing the login result and user information or an error message if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = taskService.loginUser(authRequest.getUsername(), authRequest.getPassword());

        if (user.isPresent()) {
            response.put("message", "Login successful!");
            response.put("user", user.get());
            response.put("roleId", user.get().getRoleId());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Handles user registration.
     *
     * <p>This method registers a new user with the provided credentials and role ID, returning a
     * response with user details if registration is successful.</p>
     *
     * @param authRequest the authentication request containing username, password, and role ID
     * @return a ResponseEntity containing the registration result and user information or an error message if registration fails
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody AuthRequest authRequest) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = taskService.registerUser(authRequest.getUsername(), authRequest.getPassword(), authRequest.getRoleId());

        if (user.isPresent()) {
            response.put("message", "Registration successful!");
            response.put("user", user.get());
            response.put("roleId", user.get().getRoleId());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Registration failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Retrieves the role ID for a given username.
     *
     * <p>This method fetches the role ID associated with the specified username, returning a response
     * with the role ID or an error message if the user is not found.</p>
     *
     * @param username the username whose role ID is being requested
     * @return a ResponseEntity containing the role ID or an error message if the role is not found
     */
    @GetMapping("/{username}/role")
    public ResponseEntity<String> getRoleId(@PathVariable String username) {
        return taskService.findRoleId(username)
                .map(roleId -> ResponseEntity.ok(roleId))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found"));
    }
}
