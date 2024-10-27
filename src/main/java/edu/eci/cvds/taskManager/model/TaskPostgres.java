package edu.eci.cvds.taskManager.model;


import jakarta.persistence.*;

/**
 * TaskPostgres class specifically for PostgreSQL persistence.
 * This class maps to the "tasks" table in PostgreSQL.
 */
@Entity
@Table(name = "tasks")
public class TaskPostgres extends Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @Column(name = "priority")
    private int priority;

    @Column(name = "average_development_time")
    private int averageDevelopmentTime;

    @Column(name = "user_id")
    private String userId;

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
        super(task.getId(), task.getDescription(), task.isCompleted(), task.getDifficultyLevel(), task.getPriority(), task.getAverageDevelopmentTime());
        this.id = task.getId();
        this.userId = task.getUserId();
        this.description = task.getDescription();
        this.completed = task.isCompleted();
        this.difficultyLevel = task.getDifficultyLevel();
        this.priority = task.getPriority();
        this.averageDevelopmentTime = task.getAverageDevelopmentTime();
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
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUserId(){
        return userId;
    }
}