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
     * Saves a task to the database. If a task with the same ID already exists, it will
     * update the existing task's description, completion status, difficulty level, priority,
     * and average development time.
     *
     * @param task the {@link TaskPostgres} object to be saved
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
     * Retrieves all tasks for a specific user from the database.
     *
     * @param nameUser the username of the user whose tasks are to be retrieved
     * @return a list of {@link TaskPostgres} objects for the specified user
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
     * Deletes all tasks associated with a specific user from the database.
     *
     * @param userId The ID of the user whose tasks are to be deleted.
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

    /**
     * Retrieves the user ID based on the username.
     *
     * @param username the username of the user
     * @return the user ID if found, otherwise null
     * @throws SQLException if a database access error occurs
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
     * Saves a user to the database.
     *
     * @param user the {@link User} object to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnectionPostgres.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        }
    }


    /**
     * Busca un usuario en la base de datos usando el nombre de usuario.
     *
     * @param username el nombre de usuario a buscar.
     * @return un Optional con el usuario si se encuentra, de lo contrario, un Optional vacío.
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
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
