package com.example;

public class Task {
    private String id;
    private String description;
    private boolean completed;
    private String userId;

    /**
     * Constructor de la clase Task
     */
    public Task() {
    }


    /**
     * Devuelve el ID de la tarea.
     *
     * @return el ID de la tarea.
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna un ID a la tarea.
     *
     * @param id el nuevo ID de la tarea.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve la descripción de la tarea.
     *
     * @return la descripción de la tarea.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Asigna una descripción a la tarea.
     *
     * @param description la nueva descripción de la tarea.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Indica si la tarea ha sido completada.
     *
     * @return true si la tarea está completada, false en caso contrario.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Asigna el estado de completado a la tarea.
     *
     * @param completed el nuevo estado de completado de la tarea.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Devuelve el ID del usuario asociado a la tarea.
     *
     * @return el ID del usuario.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Asigna un ID de usuario a la tarea.
     *
     * @param userId el nuevo ID de usuario asociado a la tarea.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

