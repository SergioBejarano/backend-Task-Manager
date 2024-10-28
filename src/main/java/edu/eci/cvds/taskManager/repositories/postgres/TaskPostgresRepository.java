package edu.eci.cvds.taskManager.repositories.postgres;

import edu.eci.cvds.taskManager.databasePostgres.DatabaseConnectionPostgres;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
     * Saves a task to the PostgreSQL database. If a task with the same ID exists,
     * it updates the existing record.
     *
     * @param task the task to be saved
     * @throws SQLException if a database access error occurs
     */
    public void save(TaskPostgres task) throws SQLException {
        String sql = "INSERT INTO tasks (id, description, completed, difficulty_level, priority, average_development_time, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET description = EXCLUDED.description, completed = EXCLUDED.completed, " +
                "difficulty_level = EXCLUDED.difficulty_level, priority = EXCLUDED.priority, average_development_time = EXCLUDED.average_development_time, user_id = EXCLUDED.user_id";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getId());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setString(4, task.getDifficultyLevel());
            statement.setInt(5, task.getPriority());
            statement.setDouble(6, task.getAverageDevelopmentTime());
            statement.setString(7, task.getUserId());
            statement.executeUpdate();
        }
    }

    /**
     * Retrieves all tasks associated with a specified username.
     *
     * @param nameUser the username of the user whose tasks are to be retrieved
     * @return a list of TaskPostgres objects associated with the given username
     * @throws SQLException if a database access error occurs
     */
    public List<TaskPostgres> findAllByUser(String nameUser) throws SQLException {
        List<TaskPostgres> tasks = new ArrayList<>();
        String sql = "SELECT t.id, t.description, t.completed, t.difficulty_level, t.priority, " +
                "t.average_development_time, t.user_id FROM tasks t " +
                "JOIN users u ON t.user_id = u.id " +
                "WHERE u.username = ?";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nameUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TaskPostgres task = new TaskPostgres();
                    task.setId(resultSet.getString("id"));
                    task.setDescription(resultSet.getString("description"));
                    task.setCompleted(resultSet.getBoolean("completed"));
                    task.setDifficultyLevel(resultSet.getString("difficulty_level"));
                    task.setPriority(resultSet.getInt("priority"));
                    task.setAverageDevelopmentTime(resultSet.getInt("average_development_time"));
                    task.setUserId(resultSet.getString("user_id"));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Deletes a task by its ID from the PostgreSQL database.
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
     * Deletes all tasks associated with a specified user ID.
     *
     * @param userId the ID of the user whose tasks should be deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteAllByUserId(String userId) throws SQLException {
        String deleteSql = "DELETE FROM tasks WHERE user_id = ?";
        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setString(1, userId); // Establecer el parámetro userId en la consulta
            deleteStatement.executeUpdate(); // Ejecutar la eliminación
        }
    }

    /**
     * Marks a task as completed by its ID.
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

    /**
     * Finds the user ID associated with a given username.
     *
     * @param username the username to search for
     * @return an Optional containing the user ID, if found; otherwise, an empty Optional
     */
    public Optional<String> findUserIdByUsername(String username) {
        String userId = null;
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getString("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user ID from database", e);
        }
        return Optional.ofNullable(userId);
    }

    /**
     * Saves a user to the PostgreSQL database.
     *
     * @param user the user to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRoleId());

            statement.executeUpdate();
        }
    }

    /**
     * Finds the role ID associated with a given username.
     *
     * @param username the username to search for
     * @return an Optional containing the role ID, if found; otherwise, an empty Optional
     */
    public Optional<String> findRoleId(String username) {
        String roleId = null;
        String sql = "SELECT role_id FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    roleId = resultSet.getString("role_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving role id from database", e);
        }
        return Optional.of(roleId);
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the User object if found; otherwise, an empty Optional
     */
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleId(resultSet.getString("role_id"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
