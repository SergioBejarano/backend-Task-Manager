package com.example;

import java.util.ArrayList;
import java.util.List;

public class TaskController {

    /**
     * Constructor de la clase TaskController.
     */
    public TaskController(){
        
    }

    /**
     * Agrega una nueva tarea al repositorio.
     *
     * @param task la tarea que se desea agregar.
     */
    public void addTask(Task task) {
    }

    /**
     * Obtiene las tareas asociadas a un usuario por su ID.
     *
     * @param userId el ID del usuario.
     * @return una lista de tareas asociadas al usuario.
     */
    public List<Task> getTaskByUserId(String userId) {
        return new ArrayList<>();
    }

    /**
     * Marca una tarea como completada.
     *
     * @param taskId el ID de la tarea que se desea marcar como completada.
     */
    public void completeTask(String taskId) {
    }

    /**
     * Elimina una tarea del repositorio.
     *
     * @param taskId el ID de la tarea que se desea eliminar.
     */
    public void deleteTask(String taskId) {
    }
    
}
