package edu.eci.cvds.taskManager.databasePostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnectionPostgres class provides a method to establish a
 * connection
 * to a PostgreSQL database for the Task Manager application.
 * <p>
 * This class contains the necessary database connection details such as the
 * URL, username, and password required to connect to the PostgreSQL database.
 * </p>
 */
public class DatabaseConnectionPostgres {

    private static final String URL = "jdbc:postgresql://taskmanagerdb.postgres.database.azure.com:5432/taskmanagerdb?sslmode=require";
    private static final String USER = "user_taskmanager";
    private static final String PASSWORD = "taskmanager";

    /**
     * Establishes a connection to the PostgreSQL database.
     *
     * <p>This method uses JDBC to connect to a PostgreSQL database with SSL mode required.</p>
     *
     * @return a Connection object representing the established database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
