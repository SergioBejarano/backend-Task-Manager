package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Task class represents a task in the task management application.
 * It includes information about the task's ID, description, and completion
 * status.
 */
public class Task {

    private String id;
    private String description;
    private boolean completed;

    /**
     * Default constructor for Task.
     */

    public Task() {}


    /**
     * Constructor for Task.
     *
     * @param id The ID of task
     * @param description The description of task.
     * @param completed A boolean value of task.
     */
    public Task(String id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }


    /**
     * Constructor for Task with a description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    /**
     * Gets the unique ID of the task.
     *
     * @return The ID of the task.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique ID of the task.
     *
     * @param id The ID to set for the task.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description to set for the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param completed The completion status to set.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
