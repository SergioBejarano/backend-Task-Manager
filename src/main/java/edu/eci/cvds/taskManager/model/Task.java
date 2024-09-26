package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Task class represents a task in the task management application.
 * It includes information about the task's ID, description, and completion status.
 */
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String description;
    private boolean completed;

    /**
     * Default constructor for Task.
     */
    public Task() {
        // Implementation not provided
    }

    /**
     * Constructor for Task with a description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        // Implementation not provided
    }

    /**
     * Gets the unique ID of the task.
     *
     * @return The ID of the task.
     */
    public String getId() {
        // Implementation not provided
        return null; // Placeholder return
    }

    /**
     * Sets the unique ID of the task.
     *
     * @param id The ID to set for the task.
     */
    public void setId(String id) {
        // Implementation not provided
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        // Implementation not provided
        return null; // Placeholder return
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description to set for the task.
     */
    public void setDescription(String description) {
        // Implementation not provided
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        // Implementation not provided
        return false; // Placeholder return
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed The completion status to set.
     */
    public void setCompleted(boolean completed) {
        // Implementation not provided
    }
}
