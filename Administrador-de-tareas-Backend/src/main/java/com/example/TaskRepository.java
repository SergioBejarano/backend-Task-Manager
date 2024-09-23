package com.example;

import java.util.List;

public interface TaskRepository {
    
    /**
     * Busca las tareas asociadas a un usuario por su ID.
     * 
     * @param userId el ID del usuario.
     * @return una lista de tareas asociadas al usuario.
     */
    public List<Task> findByUserId(String userId);
}
