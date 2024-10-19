package edu.eci.cvds.taskManager.controller;

import edu.eci.cvds.taskManager.model.User;
import edu.eci.cvds.taskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final TaskService taskService;

    @Autowired
    public UserController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint for user login.
     *
     * @param loginRequest A request object containing username and password.
     * @return The authenticated User object if credentials are correct.
     */
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        return taskService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
