package com.example;

import java.util.*;

public class MongoTaskRepository implements TaskRepository{

    /**
     * Busca las tareas asociadas a un usuario por su ID.
     * 
     * @param userId el ID del usuario.
     * @return una lista de tareas asociadas al usuario.
     */
    @Override
    public List<Task> findByUserId(String userId){
        return new ArrayList<>();
    }
}
