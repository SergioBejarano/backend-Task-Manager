package edu.eci.cvds.taskManager.service;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.repositories.TaskRepositoryMongo;
import edu.eci.cvds.taskManager.repositories.TaskRepositoryPostgreSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The TaskService class provides business logic for managing tasks.
 * It interacts with different Task repositories (MongoDB and PostgreSQL)
 * to perform CRUD operations on tasks based on the active database.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepositoryMongo taskRepositoryMongo;

    @Autowired
    private TaskRepositoryPostgreSQL taskRepositoryPostgreSQL;

    /**
     * Specifies the active database to use. This value is set in application.properties.
     * Possible values: "mongo", "postgresql"
     */
    @Value("${taskmanager.database.active}")
    private String activeDatabase;

    /**
     * Retrieves all tasks from the active repository based on the active database.
     *
     * @return A list of all tasks from the selected repository.
     */
    public List<Task> findAll() {
        if ("mongo".equalsIgnoreCase(activeDatabase)) {
            return taskRepositoryMongo.findAll();
        } else if ("postgresql".equalsIgnoreCase(activeDatabase)) {
            return taskRepositoryPostgreSQL.findAll();
        } else {
            throw new IllegalStateException("Unsupported database type: " + activeDatabase);
        }
    }

    /**
     * Saves a new task or updates an existing task in the active repository.
     *
     * @param task The Task object to be saved.
     * @return The saved Task object.
     */
    public Task save(Task task) {
        if ("mongo".equalsIgnoreCase(activeDatabase)) {
            return taskRepositoryMongo.save(task);
        } else if ("postgresql".equalsIgnoreCase(activeDatabase)) {
            return taskRepositoryPostgreSQL.save(task);
        } else {
            throw new IllegalStateException("Unsupported database type: " + activeDatabase);
        }
    }

    /**
     * Deletes a task by its ID in the active repository.
     *
     * @param id The ID of the task to be deleted.
     */
    public void deleteById(String id) {
        if ("mongo".equalsIgnoreCase(activeDatabase)) {
            taskRepositoryMongo.deleteById(id);
        } else if ("postgresql".equalsIgnoreCase(activeDatabase)) {
            taskRepositoryPostgreSQL.deleteById(id);
        } else {
            throw new IllegalStateException("Unsupported database type: " + activeDatabase);
        }
    }

    /**
     * Marks a task as completed by its ID in the active repository.
     *
     * @param id The ID of the task to be marked as completed.
     * @return The updated Task object.
     */
    public Task markAsCompleted(String id) {
        Task task;
        if ("mongo".equalsIgnoreCase(activeDatabase)) {
            task = taskRepositoryMongo.findById(id).orElseThrow();
        } else if ("postgresql".equalsIgnoreCase(activeDatabase)) {
            task = taskRepositoryPostgreSQL.findById(id).orElseThrow();
        } else {
            throw new IllegalStateException("Unsupported database type: " + activeDatabase);
        }
        task.setCompleted(true);
        return save(task);
    }
}