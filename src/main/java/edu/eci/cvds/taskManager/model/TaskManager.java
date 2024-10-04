package edu.eci.cvds.taskManager.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The TaskManager class manages a collection of tasks using a HashMap.
 * Each task is identified by a unique ID (String).
 */
public class TaskManager {

    private HashMap<String, Task> tasks;

    /**
     * Constructor initializes the TaskManager with an empty HashMap.
     */
    public TaskManager() {
        tasks = new HashMap<>();
    }

    /**
     * Adds a new task to the TaskManager.
     *
     * @param task The Task object to be added.
     * It is expected that the task has a unique ID assigned.
     * @throws IllegalArgumentException if the ID is null.
     */
    public void addTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new IllegalArgumentException("Task or task ID cannot be null");
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
     * @param id The ID of the task to be retrieved.
     * @return The Task object associated with the provided ID, or null if not found.
     */
    public Task getTask(String id) {
        return tasks.get(id);
    }

    /**
     * Removes a task from the TaskManager by its ID.
     *
     * @param id The ID of the task to be deleted.
     */
    public void deleteTask(String id) {
        tasks.remove(id);
    }

    /**
     * Marks a task as completed by its ID.
     *
     * @param id The ID of the task to be marked as completed.
     *           If the task is not found, no action is taken.
     */
    public void markAsCompleted(String id) {
        Task task = tasks.get(id);
        if (task != null) {
            task.setCompleted(true);
        }
    }

    /**
     * Gets tasks managed by the TaskManager.
     *
     * @return A Map containing all tasks, where the key is the task ID and the value is the Task object.
     */
    public Map<String, Task> getAllTasks() {
        return tasks;
    }
}
