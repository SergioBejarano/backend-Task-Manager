package edu.eci.cvds.taskManager.dto;

public class AuthRequest {

    private String username;
    private String password;
    private String roleId;

    /**
     * Retrieves the username from the authentication request.
     *
     * @return a String representing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the password from the authentication request.
     *
     * @return a String representing the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the role ID from the authentication request.
     *
     * @return a String representing the role ID
     */
    public String getRoleId() {
        return roleId;
    }
}

