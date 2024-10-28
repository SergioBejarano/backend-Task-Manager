package edu.eci.cvds.taskManager.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The TaskManager class manages a collection of tasks using a HashMap.
 * Each task is identified by a unique ID (String).
 */
public class TaskManager {

    private HashMap<String, Task> tasks;

    /**
     * Initializes a new TaskManager with an empty task list.
     */
    public TaskManager() {
        tasks = new HashMap<>();
    }

    /**
     * Adds a new task to the manager.
     * Validates task fields before adding.
     *
     * @param task the task to add
     * @throws IllegalArgumentException if task or task ID is null,
     *         difficulty level is invalid, priority is out of range,
     *         or average development time is negative
     */
    public void addTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new IllegalArgumentException("Task or task ID cannot be null");
        }
        switch (task.getDifficultyLevel()) {
            case "LOW":
            case "MEDIUM":
            case "HIGH":
                break;
            default:
            throw new IllegalArgumentException("Task difficulty level must be LOW, MEDIUM or HIGH");
        }
        if (task.getPriority() < 1 || task.getPriority() > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        if (task.getAverageDevelopmentTime() < 0) {
            throw new IllegalArgumentException("Average development time cannot be negative");
        }
        tasks.put(task.getId(), task);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the unique identifier of the task
     * @return the task with the specified ID, or null if not found
     */
    public Task getTask(String id) {
        return tasks.get(id);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the unique identifier of the task to delete
     */
    public void deleteTask(String id) {
        tasks.remove(id);
    }

    /**
     * Marks a task as completed by its ID.
     *
     * @param id the unique identifier of the task to mark as completed
     */
    public void markAsCompleted(String id) {
        Task task = tasks.get(id);
        if (task != null) {
            task.setCompleted(true);
        }
    }

    /**
     * Retrieves all tasks.
     *
     * @return a map of all tasks, with task IDs as keys and Task objects as values
     */
    public Map<String, Task> getAllTasks() {
        return tasks;
    }
}
