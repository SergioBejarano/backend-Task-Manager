package edu.eci.cvds.taskManager.model;

import javax.persistence.*;

import org.springframework.data.annotation.Id;


@Entity
@Table(name = "tasks")
public class TaskPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    /**
     * Default constructor for Task.
     */

    public TaskPostgres() {}

    public TaskPostgres(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.completed = task.isCompleted();
    }


    /**
     * Constructor for Task with a description.
     *
     * @param description The description of the task.
     */
    public TaskPostgres(String description) {
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
