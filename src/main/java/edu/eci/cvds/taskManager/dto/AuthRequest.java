package edu.eci.cvds.taskManager.dto;

public class AuthRequest {

    public AuthRequest(String username) {
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

