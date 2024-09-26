package edu.eci.cvds.taskManager.model;

import java.util.HashMap;

/**
 * The TaskManager class manages a collection of tasks using a HashMap.
 * Each task is identified by a unique ID (String).
 */
public class TaskManager {

    // Map to store tasks, where the key is the task ID
    private HashMap<String, Task> tasks;

    /**
     * Constructor initializes the TaskManager with an empty HashMap.
     */
    public TaskManager() {
        // Implementation not provided
    }

    /**
     * Adds a new task to the TaskManager.
     *
     * @param task The Task object to be added.
     *             It is expected that the task has a unique ID assigned.
     */
    public void addTask(Task task) {
        // Implementation not provided
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to be retrieved.
     * @return The Task object associated with the provided ID, or null if not found.
     */
    public Task getTask(String id) {
        // Implementation not provided
        return null;
    }

    /**
     * Removes a task from the TaskManager by its ID.
     *
     * @param id The ID of the task to be deleted.
     */
    public void deleteTask(String id) {
        // Implementation not provided
    }

    /**
     * Marks a task as completed by its ID.
     *
     * @param id The ID of the task to be marked as completed.
     *           If the task is not found, no action is taken.
     */
    public void markAsCompleted(String id) {
        // Implementation not provided
    }

    /**
     * Retrieves all tasks managed by the TaskManager.
     *
     * @return A Map containing all tasks, where the key is the task ID and the value is the Task object.
     */
    public HashMap<String, Task> getAllTasks() {
        // Implementation not provided
        return null;
    }
}
