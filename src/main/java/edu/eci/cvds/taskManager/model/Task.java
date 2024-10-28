package edu.eci.cvds.taskManager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The Task class represents a task in the task management application.
 * It includes information about the task's ID, description, completion status
 * level of difficulty, priority and average development time.
 */
public class Task {


    private String userId;
    private String id;
    private String description;
    private boolean completed;
    private String difficultyLevel;
    private int priority;
    private int averageDevelopmentTime;
    public static final List<String> difficultyLevels =  new ArrayList<>(Arrays.asList("LOW", "MEDIUM", "HIGH"));

    /**
     * Default constructor that initializes a task with a unique ID.
     */
    public Task() {this.id = UUID.randomUUID().toString();}

    /**
     * Constructs a task with specified attributes.
     *
     * @param id                  unique identifier of the task
     * @param description         brief description of the task
     * @param completed           completion status of the task
     * @param difficultyLevel     difficulty level of the task (LOW, MEDIUM, or HIGH)
     * @param priority            priority level of the task (1 to 5)
     * @param averageDevelopmentTime estimated development time in hours
     */
    public Task(String id, String description, boolean completed, String difficultyLevel, int priority, int averageDevelopmentTime) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.difficultyLevel = difficultyLevel;
        this.priority = priority;
        this.averageDevelopmentTime = averageDevelopmentTime;
    }

    /**
     * Constructs a task with a description, setting default values for other attributes.
     *
     * @param description description of the task
     */
    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.difficultyLevel = "LOW";
        this.priority = 1;
        this.averageDevelopmentTime = 0;
    }

    /**
     * Retrieves the task ID.
     *
     * @return a String representing the task ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the task ID.
     *
     * @param id unique identifier of the task
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the task description.
     *
     * @return a String representing the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     *
     * @param description description of the task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if the task is completed.
     *
     * @return a boolean indicating task completion status
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the task's completion status.
     *
     * @param completed true if the task is completed, false otherwise
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Retrieves the difficulty level of the task.
     *
     * @return a String representing the task difficulty level
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the difficulty level of the task.
     *
     * @param difficultyLevel difficulty level (LOW, MEDIUM, or HIGH)
     * @throws IllegalArgumentException if the difficulty level is not valid
     */
    public void setDifficultyLevel(String difficultyLevel) {
        if (!difficultyLevels.contains(difficultyLevel)) {
            throw new IllegalArgumentException("Difficulty level must be LOW, MEDIUM, or HIGH");
        }
        this.difficultyLevel = difficultyLevel.toUpperCase();
    }

    /**
     * Retrieves the task priority level.
     *
     * @return an integer representing the task priority level
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the task priority level.
     *
     * @param priority priority level (1 to 5)
     * @throws IllegalArgumentException if the priority is outside the range of 1 to 5
     */
    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        this.priority = priority;
    }

    /**
     * Retrieves the average development time for the task.
     *
     * @return an integer representing the average development time in hours
     */
    public int getAverageDevelopmentTime() {
        return averageDevelopmentTime;
    }

    /**
     * Sets the average development time for the task.
     *
     * @param averageDevelopmentTime estimated time in hours
     * @throws IllegalArgumentException if the time is negative
     */
    public void setAverageDevelopmentTime(int averageDevelopmentTime) {
        if (averageDevelopmentTime < 0) {
            throw new IllegalArgumentException("Average development time cannot be negative");
        }
        this.averageDevelopmentTime = averageDevelopmentTime;
    }

    /**
     * Sets the user ID associated with the task.
     *
     * @param userId unique identifier of the user
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the user ID associated with the task.
     *
     * @return a String representing the user ID
     */
    public String getUserId(){
        return userId;
    }

}
