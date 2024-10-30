package edu.eci.cvds.taskManager.service;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
import edu.eci.cvds.taskManager.model.User;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.repositories.mongo.TaskMongoRepository;
import edu.eci.cvds.taskManager.repositories.mongo.UserRepository;
import edu.eci.cvds.taskManager.repositories.postgres.TaskPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * The {@code TaskService} class provides business logic for managing tasks within the application.
 * It handles operations related to tasks, including creating, updating, deleting, and retrieving tasks,
 * as well as user authentication and registration.
 */
@Service
public class TaskService {

    private final TaskMongoRepository taskMongoRepository;
    private static UserRepository userRepository;
    private final TaskPostgresRepository taskPostgresRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Constructor to inject dependencies for TaskMongoRepository, TaskPostgresRepository, and UserRepository.
     *
     * @param taskMongoRepository the repository for MongoDB tasks
     * @param taskPostgresRepository the repository for PostgreSQL tasks
     * @param NewUserRepository the user repository for handling user data
     */
    @Autowired
    public TaskService(TaskMongoRepository taskMongoRepository, TaskPostgresRepository taskPostgresRepository,UserRepository NewUserRepository) {
        this.taskMongoRepository = taskMongoRepository;
        this.taskPostgresRepository = taskPostgresRepository;
        this.userRepository = NewUserRepository;

    }

    /**
     * Retrieves all tasks associated with a specific user.
     *
     * @param nameUser the username of the user
     * @return a list of TaskPostgres objects associated with the specified user
     */
    public List<TaskPostgres> findAll(String nameUser) {
        try {
            return taskPostgresRepository.findAllByUser(nameUser);
        } catch (SQLException e) {
            System.err.println("Error retrieving tasks for user: " + nameUser);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves the authenticated user's ID by username.
     *
     * @param username the username of the user
     * @return the user ID associated with the specified username
     * @throws UsernameNotFoundException if the user is not found
     */
    private String getAuthenticatedUser(String username) {
        return taskPostgresRepository.findUserIdByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Saves a task associated with the authenticated user.
     *
     * @param userName the username of the user
     * @param task the task object to be saved
     * @return the saved Task object
     */
    public Task save(String userName, Task task)  {
        task.setUserId(getAuthenticatedUser(userName));

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
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to be deleted
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
     * Deletes all tasks associated with a specific user.
     *
     * @param userName the username of the user
     */
    public void deleteAllByUserId(String userName)  {
        String userID = getAuthenticatedUser(userName);
        try {
            taskPostgresRepository.deleteAllByUserId(userID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        taskMongoRepository.deleteAllByUserId(userID);
    }

    /**
     * Marks a task as completed by its ID.
     *
     * @param id the ID of the task to be marked as completed
     * @return the updated Task object
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
     * Generates a list of random tasks for a specific user and saves them.
     *
     * @param userName the username of the user for whom tasks are generated
     * @return a list of randomly generated Task objects
     */
    public List<Task> generateRandomTasks(String userName) {
        Random random = new Random();
        int numTasks = random.nextInt(21) + 10;
        List<Task> tasks = new ArrayList<>();

        for (int i = 1; i <= numTasks; i++) {
            Task task = new Task();
            List<String> difficultyLevels = Task.difficultyLevels;

            task.setDescription("Tarea generada aleatoriamente número " + i);
            task.setCompleted(random.nextBoolean());
            task.setPriority(random.nextInt(5) + 1);
            task.setDifficultyLevel(difficultyLevels.get(random.nextInt(difficultyLevels.size())));
            task.setAverageDevelopmentTime(random.nextInt(10) + 1);

            this.save(userName, task);
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * Authenticates a user by checking the username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return an Optional containing the authenticated User if successful; otherwise, an empty Optional
     */
    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOptional = taskPostgresRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            } else {
                System.out.println("Contraseña incorrecta");
            }
        } else {
            System.out.println("Usuario no encontrado");
        }
        return Optional.empty();
    }

    /**
     * Registers a new user with the specified username, password, and role ID.
     *
     * @param username the desired username for the new user
     * @param password the desired password for the new user
     * @param roleId the role ID associated with the new user
     * @return an Optional containing the registered User if successful; otherwise, an empty Optional
     */
    public Optional<User> registerUser(String username, String password, String roleId) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(encodedPassword);
            user.setRoleId(roleId);
            taskPostgresRepository.saveUser(user);
            String generatedId = taskPostgresRepository.getUserIdByUsername(username);
            user.setId(String.valueOf(generatedId));
            userRepository.save(user);
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Finds the role ID associated with a specified username.
     *
     * @param username the username of the user
     * @return an Optional containing the role ID if found; otherwise, an empty Optional
     */
    public Optional<String> findRoleId(String username) {
        return taskPostgresRepository.findRoleId(username);
    }

}
