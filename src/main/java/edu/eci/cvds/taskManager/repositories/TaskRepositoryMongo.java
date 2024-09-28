package edu.eci.cvds.taskManager.repositories;

import edu.eci.cvds.taskManager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The TaskRepositoryMongo interface provides the data access layer for Task entities.
 * It extends the MongoRepository interface, which provides basic CRUD operations.
 */
public interface TaskRepositoryMongo extends MongoRepository<Task, String> {
    // No additional methods are defined; the default CRUD operations are inherited from MongoRepository.
}
