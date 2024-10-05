package edu.eci.cvds.taskManager.repositories.postgres;

import edu.eci.cvds.taskManager.databasePostgres.DatabaseConnectionPostgres;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code TaskPostgresRepository} class provides methods for interacting with the
 * PostgreSQL database for task management operations.
 * <p>
 * This class is responsible for CRUD (Create, Read, Update, Delete) operations
 * related to tasks stored in the PostgreSQL database.
 * </p>
 */
@Repository
public class TaskPostgresRepository {


    /**
     * Saves a task to the database. If a task with the same ID already exists, it will
     * update the existing task's description and completion status.
     *
     * @param task the {@link TaskPostgres} object to be saved
     * @throws SQLException if a database access error occurs
     */
    public void save(TaskPostgres task) throws SQLException {
        String sql = "INSERT INTO tasks (id, description, completed) VALUES (?, ?, ?) ON CONFLICT (id) DO UPDATE SET description = EXCLUDED.description, completed = EXCLUDED.completed";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getId());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.executeUpdate();
        }
    }


    /**
     * Retrieves all tasks from the database.
     *
     * @return a list of {@link TaskPostgres} objects
     * @throws SQLException if a database access error occurs
     */
    public List<TaskPostgres> findAll() throws SQLException {
        List<TaskPostgres> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                TaskPostgres task = new TaskPostgres();
                task.setId(resultSet.getString("id"));
                task.setDescription(resultSet.getString("description"));
                task.setCompleted(resultSet.getBoolean("completed"));
                tasks.add(task);
            }
        }
        return tasks;
    }


    /**
     * Deletes a task from the database by its ID.
     *
     * @param id the ID of the task to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteById(String id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        }
    }


    /**
     * Marks a task as completed in the database.
     *
     * @param id the ID of the task to be marked as completed
     * @throws SQLException if a database access error occurs
     */
    public void markAsCompleted(String id) throws SQLException {
        String sql = "UPDATE tasks SET completed = true WHERE id = ?";
        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        }
    }
}
