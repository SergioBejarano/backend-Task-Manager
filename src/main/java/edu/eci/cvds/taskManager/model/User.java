package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "users")
public class User {

    @Id
    private String id; // Unique identifier for the user
    private String username; // Username of the user
    private String password; // Password of the user
    private String roleId; // Role identifier to define user roles and permissions

    /**
     * Default constructor for User.
     */
    public User() {}

    /**
     * Constructs a User with specified username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the unique identifier of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the unique identifier of the user
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role identifier of the user.
     *
     * @return the role identifier of the user
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets the role identifier of the user.
     *
     * @param roleId the role identifier of the user
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
