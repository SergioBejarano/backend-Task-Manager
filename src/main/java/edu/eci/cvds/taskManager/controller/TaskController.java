package edu.eci.cvds.taskManager.controller;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
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
    @GetMapping("/{userName}")
    public List<TaskPostgres> getAllTasks(@PathVariable String userName) {
        return taskService.findAll(userName);
    }

    /**
     * Creates a new task.
     *
     * @param task The Task object to be created.
     * @return The created Task object.
     */
    @PostMapping()
    public Task createTask(@RequestBody Task task)  {
        return taskService.save(task);
    }

    /**
     * Marks a task as completed.
     *
     * @param id The ID of the task to be marked as completed.
     * @return ResponseEntity containing the updated Task object.
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable String id) {
        Task updatedTask = taskService.markAsCompleted(id);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to be deleted.
     * @return ResponseEntity indicating the outcome of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id)  {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all tasks.
     */
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllTasks()  {
        taskService.deleteAllByUserId();
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes tasks
     */

    /**
     * Generate a list of random tasks.
     *
     * @return a list of randomly generated tasks.
     */
    @PostMapping("/task/randomTasks")
    public List<Task> generateRandomTasks() {
        return taskService.generateRandomTasks();
    }
}
