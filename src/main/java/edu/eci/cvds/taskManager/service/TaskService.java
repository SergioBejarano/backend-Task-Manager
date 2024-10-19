package edu.eci.cvds.taskManager.service;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
import edu.eci.cvds.taskManager.model.User;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.repositories.mongo.TaskMongoRepository;
import edu.eci.cvds.taskManager.repositories.UserRepository;
import edu.eci.cvds.taskManager.repositories.postgres.TaskPostgresRepository;
import edu.eci.cvds.taskManager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**
 * The TaskService class provides business logic for managing tasks.
 * It interacts with the Mongo and Postgres repositories to perform CRUD operations on tasks.
 */
@Service
public class TaskService {

    private final TaskMongoRepository taskMongoRepository;

    private static UserRepository userRepository;
    private final TaskPostgresRepository taskPostgresRepository;

    @Autowired
    public TaskService(TaskMongoRepository taskMongoRepository, TaskPostgresRepository taskPostgresRepository,UserRepository NewUserRepository) {
        this.taskMongoRepository = taskMongoRepository;
        this.taskPostgresRepository = taskPostgresRepository;
        this.userRepository = NewUserRepository;

    }

    /**
     * Retrieves all tasks from both MongoDB and Postgres repositories.
     *
     * @return A list of all tasks from both databases filtered by the authenticated user.
     */
    public List<TaskMongo> findAll() {
        User authenticatedUser = getAuthenticatedUser();
        String userID = authenticatedUser.getId();
        List<TaskMongo> tasksMongo = taskMongoRepository.findAll();
        List<TaskMongo> filteredTasks = new ArrayList<>();
        for (TaskMongo task : tasksMongo) {
            if (task.getUserId().equals(userID)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;}

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return The authenticated User object.
     */
    private User getAuthenticatedUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The authenticated User object.
     * @throws RuntimeException if authentication fails.
     */
    public User login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (((Optional<?>) user).isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public static void registerUser(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    /**
     * Saves a new task or updates an existing task in both MongoDB and Postgres repositories.
     *
     * @param task The Task object to be saved.
     * @return The saved Task object.
     */
    public Task save(Task task)  {
        User authenticatedUser = getAuthenticatedUser();
        task.setUserId(authenticatedUser.getId());
        TaskMongo taskMongo = new TaskMongo(task);
        TaskPostgres taskPostgres = new TaskPostgres(task);
        try {
            taskPostgresRepository.save(taskPostgres);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taskMongoRepository.save(taskMongo);
    }

    /**
     * Deletes a task by its ID in both MongoDB and Postgres repositories.
     *
     * @param id The ID of the task to be deleted.
     */
    public void deleteById(String id)  {
        try {
            taskPostgresRepository.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try {
            taskPostgresRepository.markAsCompleted(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taskMongoRepository.save(taskMongo);
    }

    /**
     * Generates a list of random tasks. This method creates a random number of tasks (between 100 and 1000) and assigns random values
     * to their various attributes. Each task is generated with the following values:
     * - **Description**: A string indicating that the task was randomly generated along with its number.
     * - **Completed**: A random boolean value indicating whether the task is completed or not.
     * - **Priority**: A random integer between 0 and 5 representing the priority of the task.
     * - **Difficulty level**: A random value from (LOW, MEDIUM, HIGH).
     * - **Average development time**: A positive integer representing the estimated time to develop the task.
     * Each generated task is saved using the `save()` method and added to the list of tasks.
     *
     * @return a list of randomly generated tasks.
     */
    public List<Task> generateRandomTasks() {
        Random random = new Random();
        int numTasks = random.nextInt(901) + 100; // Genera entre 100 y 1000 tasks
        List<Task> tasks = new ArrayList<>();

        for (int i = 1; i <= numTasks; i++) {
            Task task = new Task();
            List<String> difficultyLevels = Task.difficultyLevels;

            task.setDescription("Tarea generada aleatoriamente número " + i);
            task.setCompleted(random.nextBoolean());
            task.setPriority(random.nextInt(5) + 1);
            task.setDifficultyLevel(difficultyLevels.get(random.nextInt(difficultyLevels.size())));
            task.setAverageDevelopmentTime(Math.abs(random.nextInt()));

            this.save(task);
            tasks.add(task);
        }
        return tasks;
    }
}
