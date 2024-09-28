package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id; // Para MongoDB
import org.springframework.data.mongodb.core.mapping.Document; // Para MongoDB
import javax.persistence.Entity; // Para JPA
import javax.persistence.GeneratedValue; // Para JPA
import javax.persistence.GenerationType; // Para JPA
import javax.persistence.Table; // Para JPA
import javax.persistence.Column; // Para JPA
import javax.persistence.Id; // Para JPA

/**
 * The Task class represents a task in the task management application.
 * It includes information about the task's ID, description, and completion
 * status.
 */
@Document(collection = "tasks") // Para MongoDB
@Entity // Para PostgreSQL (JPA)
@Table(name = "tasks") // Para PostgreSQL (tabla "tasks")
public class Task {

    @Id // Anotación para JPA
    @org.springframework.data.annotation.Id // Anotación para MongoDB
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para PostgreSQL (JPA)
    private String id;

    @Column(name = "description") // Para PostgreSQL (JPA)
    private String description;

    @Column(name = "completed") // Para PostgreSQL (JPA)
    private boolean completed;

    /**
     * Default constructor for Task.
     */

    public Task() {}

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
