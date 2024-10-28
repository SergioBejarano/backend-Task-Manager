package edu.eci.cvds.taskManager.service;

import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
import edu.eci.cvds.taskManager.model.User;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.repositories.mongo.TaskMongoRepository;
import edu.eci.cvds.taskManager.repositories.UserRepository;
import edu.eci.cvds.taskManager.repositories.postgres.TaskPostgresRepository;
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
import org.springframework.security.core.userdetails.UserDetails;



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
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * Retrieves all tasks from both MongoDB and Postgres repositories.
     *
     * @return A list of all tasks from both databases.
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
     * Retrieves the currently authenticated user's ID from the security context.
     *
     * @return The authenticated user's ID.
     */
    private String getAuthenticatedUser(String username) {

        return taskPostgresRepository.findUserIdByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }



    /**
     * Saves a new task or updates an existing task in both MongoDB and Postgres repositories.
     *
     * @param task The Task object to be saved.
     * @return The saved Task object.
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
     * Deletes all tasks associated with the authenticated user from both MongoDB and Postgres repositories.
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
    public List<Task> generateRandomTasks(String userName) {
        Random random = new Random();
        int numTasks = random.nextInt(21) + 10; // Genera entre 10 y 30 tasks
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
     * Authenticates a user by validating the provided username and password.
     *
     * This method retrieves a user from the database using the given username.
     * If the user is found, it compares the provided password with the stored
     * password using a password encoder. If the passwords match, it returns
     * an Optional containing the User object. If the user is not found or the
     * passwords do not match, it returns an empty Optional.
     *
     * @param username the username of the user trying to log in
     * @param password the password provided by the user for authentication
     * @return an Optional<User> containing the authenticated User if the login is successful,
     *         or an empty Optional if the user is not found or the password is incorrect
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
     * Registers a new user in the system with the specified username, password, and role ID.
     *
     * This method creates a new User object, encodes the provided password using
     * BCrypt, and assigns the role ID to the new user. It saves the user to the
     * database and returns an Optional containing the newly created User. If any
     * SQLException occurs during the process, it prints the stack trace and returns
     * an empty Optional to indicate failure.
     *
     * @param username the username for the new user
     * @param password the password for the new user; this will be encoded before saving
     * @param roleId the ID of the role assigned to the new user
     * @return an Optional<User> containing the newly registered User if successful,
     *         or an empty Optional if registration fails due to an SQLException
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
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Retrieves the role_id for a given username.
     *
     * @param username the username of the user
     * @return an Optional containing the role_id if found, empty otherwise
     */
    public Optional<String> findRoleId(String username) {
        return taskPostgresRepository.findRoleId(username);
    }

}
