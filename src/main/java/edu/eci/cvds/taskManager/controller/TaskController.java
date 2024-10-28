package edu.eci.cvds.taskManager.controller;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
     * Retrieves all tasks for a specified user.
     *
     * <p>This endpoint returns a list of tasks associated with the given username.</p>
     *
     * @param userName the username for whom tasks are being retrieved
     * @return a list of TaskPostgres objects representing the user's tasks
     */
    @GetMapping("/{userName}")
    public List<TaskPostgres> getAllTasks(@PathVariable String userName) {
        return taskService.findAll(userName);
    }

    /**
     * Creates a new task for a specified user.
     *
     * <p>This endpoint creates and saves a task associated with the given username.</p>
     *
     * @param user the username for whom the task is being created
     * @param task the task object to be created
     * @return the created Task object
     */
    @PostMapping("/{user}")
    public Task createTask(@PathVariable String user, @RequestBody Task task)  {
        return taskService.save(user, task);
    }

    /**
     * Marks a specific task as completed.
     *
     * <p>This endpoint updates the status of a task to completed based on its ID.</p>
     *
     * @param id the ID of the task to be marked as completed
     * @return a ResponseEntity containing the updated Task object
     */
    @PutMapping("/{user}/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable String id) {
        Task updatedTask = taskService.markAsCompleted(id);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a specific task by its ID.
     *
     * <p>This endpoint removes the task associated with the provided ID from the database.</p>
     *
     * @param id the ID of the task to be deleted
     * @return a ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{user}/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id)  {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all tasks for a specified user.
     *
     * <p>This endpoint removes all tasks associated with the given username.</p>
     *
     * @param user the username for whom all tasks are being deleted
     * @return a ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{user}")
    public ResponseEntity<Void> deleteAllTasks(@PathVariable String user)  {
        taskService.deleteAllByUserId(user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Generates random tasks for a specified user.
     *
     * <p>This endpoint creates a set of random tasks associated with the given username.</p>
     *
     * @param user the username for whom random tasks are being generated
     * @return a list of randomly generated Task objects
     */
    @PostMapping("/{user}/task/randomTasks")
    public List<Task> generateRandomTasks(@PathVariable String user) {
        return taskService.generateRandomTasks(user);
    }

    /**
     * Fetches tasks for a specified user with additional exception handling.
     *
     * <p>This endpoint retrieves tasks associated with the given username, handling potential SQL exceptions.</p>
     *
     * @param userName the username for whom tasks are being fetched
     * @return a ResponseEntity containing a list of TaskPostgres objects or an error message if an exception occurs
     * @throws SQLException if a database access error occurs
     */
    @GetMapping("/{userName}/fetchTasks")
    public ResponseEntity<List<TaskPostgres>> fetchTasks(@PathVariable String userName) throws SQLException {
        List<TaskPostgres> tasks = taskService.findAll(userName);
        return ResponseEntity.ok(tasks);
    }
}
