package edu.eci.cvds.taskManager.service;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The TaskService class provides business logic for managing tasks.
 * It interacts with the TaskRepository to perform CRUD operations on tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Retrieves all tasks from the repository.
     *
     * @return A list of all tasks.
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Saves a new task or updates an existing task.
     *
     * @param task The Task object to be saved.
     * @return The saved Task object.
     */
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to be deleted.
     */
    public void deleteById(String id) {
        taskRepository.deleteById(id);
    }

    /**
     * Marks a task as completed by its ID.
     *
     * @param id The ID of the task to be marked as completed.
     * @return The updated Task object.
     */
    public Task markAsCompleted(String id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setCompleted(true);
        return taskRepository.save(task);
    }
}
