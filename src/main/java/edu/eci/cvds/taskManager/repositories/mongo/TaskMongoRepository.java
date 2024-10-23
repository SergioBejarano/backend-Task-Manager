package edu.eci.cvds.taskManager.repositories.mongo;

import edu.eci.cvds.taskManager.model.TaskMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The TaskMongoRepository interface provides the data access layer for Task entities.
 * It extends the MongoRepository interface, which provides basic CRUD operations.
 */
@Repository
public interface TaskMongoRepository extends MongoRepository<TaskMongo, String> {

    /**
     * Finds all tasks associated with the given user ID.
     *
     * @param userId the ID of the user for whom to find tasks
     * @return a list of tasks associated with the specified user ID
     */
    List<TaskMongo> findByUserId(String userId);

}
