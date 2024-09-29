package edu.eci.cvds.taskManager.model;

import javax.persistence.*;

/**
 * TaskPostgres class specifically for PostgreSQL persistence.
 * This class maps to the "tasks" table in PostgreSQL.
 */
@Entity
@Table(name = "tasks")
public class TaskPostgres extends Task {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private boolean completed;

    /**
     * Default constructor for TaskPostgres.
     */
    public TaskPostgres() {
        super();
    }

    /**
     * Constructor for Task with a task object.
     *
     * @param task A task object to copy data from.
     */
    public TaskPostgres(Task task) {
        super(task.getId(), task.getDescription(), task.isCompleted());
        this.id = task.getId();
        this.description = task.getDescription();
        this.completed = task.isCompleted();
    }

    /**
     * Constructor for TaskPostgres with a description.
     *
     * @param description The description of the task.
     */
    public TaskPostgres(String description) {
        super(description);
    }

    /**
     * Gets the unique ID of the task.
     *
     * @return The ID of the task.
     */
    @Override
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

}
