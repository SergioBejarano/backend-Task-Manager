package edu.eci.cvds.taskManager.repositories;

import edu.eci.cvds.taskManager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
