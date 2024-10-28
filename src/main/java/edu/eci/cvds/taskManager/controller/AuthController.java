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


    public AuthController(AuthenticationManager authenticationManager, TaskService taskService) {
        this.authenticationManager = authenticationManager;
        this.taskService = taskService;
    }

    /**
     * Authenticates a user by checking the provided username and password.
     *
     * This method searches for a user in the database by the given username.
     * If the user is found, it verifies the provided password against the stored
     * password using the password encoder. If the authentication is successful,
     * it returns an Optional containing the User object; otherwise, it returns
     * an empty Optional.
     *
     * @param username the username of the user attempting to log in
     * @param password the password provided by the user for authentication
     * @return an Optional<User> containing the authenticated User if successful,
     *         or an empty Optional if the username is not found or the password is incorrect
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
     * Registers a new user in the system.
     *
     * This method creates a new User object with the provided username, password,
     * and role ID. The password is encoded using BCrypt before being stored in the database.
     * If the registration is successful, it saves the User object to the database and
     * returns an Optional containing the newly created User. If any SQLException occurs,
     * it catches the exception, prints the stack trace, and returns an empty Optional.
     *
     * @param username the username of the new user to be registered
     * @param password the password for the new user; it will be encoded before storage
     * @param roleId the role ID to be assigned to the new user
     * @return an Optional<User> containing the newly registered User if successful,
     *         or an empty Optional if registration fails due to an SQLException
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
     * Retrieve the role id for a given username.
     *
     * @param username the username of the user
     * @return ResponseEntity containing the role id or an error message
     */
    @GetMapping("/{username}/role")
    public ResponseEntity<String> getRoleId(@PathVariable String username) {
        return taskService.findRoleId(username)
                .map(roleId -> ResponseEntity.ok(roleId))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found"));
    }
}
