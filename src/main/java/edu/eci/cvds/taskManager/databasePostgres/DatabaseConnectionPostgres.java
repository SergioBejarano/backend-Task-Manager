package edu.eci.cvds.taskManager.databasePostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnectionPostgres class provides a method to establish a connection
 * to a PostgreSQL database for the Task Manager application.
 * <p>
 * This class contains the necessary database connection details such as the
 * URL, username, and password required to connect to the PostgreSQL database.
 * </p>
 */
public class DatabaseConnectionPostgres {

    private static final String URL = "jdbc:postgresql://localhost:5432/Task_Manager";
    private static final String USER = "user_taskmanager";
    private static final String PASSWORD = "taskmanager";

    /**
     * Establishes a connection to the PostgreSQL database.
     *
     * @return Connection object representing the database connection
     * @throws SQLException if a database access error occurs or the url is
     *                      null
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
