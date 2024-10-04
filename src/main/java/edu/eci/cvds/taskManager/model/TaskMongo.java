package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * TaskMongo class specifically for MongoDB persistence.
 * This class maps to the "tasks" collection in MongoDB.
 */
@Document(collection = "tasks")
public class TaskMongo extends Task {

    @Id
    private String id;

    /**
     * Default constructor for TaskMongo.
     */
    public TaskMongo() {
        super();
    }

    /**
     * Constructor that creates a TaskMongo from a Task object.
     *
     * @param task A task object to copy data from.
     */
    public TaskMongo(Task task) {
        super(task.getId(), task.getDescription(), task.isCompleted(), task.getDifficultyLevel(), task.getPriority(), task.getAverageDevelopmentTime());
        this.id = task.getId();
    }

    /**
     * Constructor for TaskMongo with a description.
     *
     * @param description The description of the task.
     */
    public TaskMongo(String description) {
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
}
