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
    private String getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = "sergioBejarano";
            return taskPostgresRepository.findUserIdByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        } else if (principal instanceof String username) {
            return taskPostgresRepository.findUserIdByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        } else {
            throw new UsernameNotFoundException("User not authenticated");
        }
    }



    /**
     * Saves a new task or updates an existing task in both MongoDB and Postgres repositories.
     *
     * @param task The Task object to be saved.
     * @return The saved Task object.
     */
    public Task save(Task task)  {
        task.setUserId(getAuthenticatedUser());

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
    public void deleteAllByUserId()  {
        String userID = getAuthenticatedUser();
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


    public Optional<User> registerUser(String username, String password) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);

            User user = new User();
            user.setUsername(username);
            user.setPassword(encodedPassword);

            taskPostgresRepository.saveUser(user);
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
