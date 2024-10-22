package edu.eci.cvds.taskManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "users")  // Indica que esta clase representa un documento en la colección "users"
public class User {

    @Id
    private String id;  // MongoDB usará este campo como el identificador único del documento
    private String username;
    private String password;

    // Constructor vacío (requerido por algunas bibliotecas de serialización)
    public User() {}

    // Constructor con parámetros
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
