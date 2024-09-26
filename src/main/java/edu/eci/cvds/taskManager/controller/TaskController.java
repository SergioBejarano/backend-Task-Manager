package edu.eci.cvds.taskManager.controller;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The TaskController class handles HTTP requests for managing tasks.
 * It provides endpoints to create, retrieve, update, and delete tasks.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Retrieves all tasks.
     *
     * @return A list of all tasks.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        // Implementation not provided
        return null;
    }

    /**
     * Creates a new task.
     *
     * @param task The Task object to be created.
     * @return The created Task object.
     */
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        // Implementation not provided
        return null;
    }

    /**
     * Marks a task as completed.
     *
     * @param id The ID of the task to be marked as completed.
     * @return ResponseEntity containing the updated Task object.
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable String id) {
        // Implementation not provided
        return null;
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to be deleted.
     * @return ResponseEntity indicating the outcome of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        // Implementation not provided
        return null;
    }
}
