package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * TaskMongo class specifically for MongoDB persistence.
 * This class maps to the "tasks" collection in MongoDB.
 */
@Document(collection = "tasks")
public class TaskMongo extends Task {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    /**
     * Default constructor for TaskMongo.
     */
    public TaskMongo() {
        super();
    }

    /**
     * Constructs a TaskMongo instance based on a Task object.
     *
     * @param task the Task object to copy properties from
     */
    public TaskMongo(Task task) {
        super(task.getId(), task.getDescription(), task.isCompleted(), task.getDifficultyLevel(), task.getPriority(), task.getAverageDevelopmentTime());
        this.id = task.getId();
        this.userId = task.getUserId();
    }

    /**
     * Constructs a TaskMongo instance with a specified description.
     * Sets default values for other task properties.
     *
     * @param description the description of the task
     */
    public TaskMongo(String description) {
        super(description);
    }

    /**
     * Gets the ID of the task.
     *
     * @return the unique identifier of the task
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the user ID associated with the task.
     *
     * @param userId the unique identifier of the user
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
