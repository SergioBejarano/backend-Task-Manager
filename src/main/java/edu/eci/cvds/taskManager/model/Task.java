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
     * Default constructor for Task.
     */

    public Task() {this.id = UUID.randomUUID().toString();}


    /**
     * Constructor for Task.
     *
     * @param id The ID of task
     * @param description The description of task.
     * @param completed A boolean value of task.
     * @param difficultyLevel The level of difficulty of the task (Low, Medium, High).
     * @param priority The priority of the task, where 1 is the lowest priority and 5 is the highest.
     * @param averageDevelopmentTime The average time required to complete the task, measured in hours.
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
     * Constructor for Task with a description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.difficultyLevel = "LOW";
        this.priority = 1;
        this.averageDevelopmentTime = 0;
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

    /**
     * Gets the difficulty level of the task.
     *
     * @return The difficulty level of the task.
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the difficulty level of the task.
     *
     * @param difficultyLevel The difficulty level to set.
     * @throws IllegalArgumentException if the difficulty level is not "LOW", "MEDIUM", or "HIGH".
     */
    public void setDifficultyLevel(String difficultyLevel) {
        if (!difficultyLevels.contains(difficultyLevel)) {
            throw new IllegalArgumentException("Difficulty level must be LOW, MEDIUM, or HIGH");
        }
        this.difficultyLevel = difficultyLevel.toUpperCase();
    }

    /**
     * Gets the priority of the task.
     *
     * @return The priority of the task, where 1 is the lowest priority and 5 is the highest.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority The priority to set, which must be between 1 and 5.
     * @throws IllegalArgumentException if the priority is not between 1 and 5.
     */
    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        this.priority = priority;
    }

    /**
     * Gets the average development time for the task.
     *
     * @return The average development time in hours.
     */
    public int getAverageDevelopmentTime() {
        return averageDevelopmentTime;
    }

    /**
     * Sets the average development time for the task.
     *
     * @param averageDevelopmentTime The average development time to set, which must be non-negative.
     * @throws IllegalArgumentException if the average development time is negative.
     */
    public void setAverageDevelopmentTime(int averageDevelopmentTime) {
        if (averageDevelopmentTime < 0) {
            throw new IllegalArgumentException("Average development time cannot be negative");
        }
        this.averageDevelopmentTime = averageDevelopmentTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

}
