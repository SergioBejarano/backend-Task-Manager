package edu.eci.cvds.taskManager.service;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.repositories.TaskMongoRepository;
import edu.eci.cvds.taskManager.repositories.TaskPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The TaskService class provides business logic for managing tasks.
 * It interacts with the Mongo and Postgres repositories to perform CRUD operations on tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskMongoRepository taskMongoRepository;

    @Autowired
    private TaskPostgresRepository taskPostgresRepository;

    /**
     * Retrieves all tasks from both MongoDB and Postgres repositories.
     *
     * @return A list of all tasks from both databases.
     */
    public List<TaskMongo> findAll() {
        return taskMongoRepository.findAll();

    }

    /**
     * Saves a new task or updates an existing task in both MongoDB and Postgres repositories.
     *
     * @param task The Task object to be saved.
     * @return The saved Task object.
     */
    public Task save(Task task) {
        TaskMongo taskMongo = new TaskMongo(task);
        TaskPostgres taskPostgres  = new TaskPostgres(task);
        taskPostgresRepository.saveAndFlush(taskPostgres);
        return taskMongoRepository.save(taskMongo);
    }

    /**
     * Deletes a task by its ID in both MongoDB and Postgres repositories.
     *
     * @param id The ID of the task to be deleted.
     */
    public void deleteById(String id) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        taskPostgresRepository.deleteAllByIdInBatch(ids);
        taskMongoRepository.deleteById(id);
    }

    /**
     * Marks a task as completed by its ID in both MongoDB and Postgres repositories.
     *
     * @param id The ID of the task to be marked as completed.
     * @return The updated Task object.
     */
    public Task markAsCompleted(String id) {
        TaskMongo taskMongo = taskMongoRepository.findById(id).orElseThrow();
        taskMongo.setCompleted(true);
        //TaskPostgres taskPostgres = taskPostgresRepository.getReferenceById(id);
        //taskPostgres.setCompleted(true);
        //taskPostgresRepository.saveAndFlush(taskPostgres);
        return taskMongoRepository.save(taskMongo);
    }
}
