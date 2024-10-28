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
     * Retrieves a list of TaskMongo entities associated with a specified user ID.
     *
     * @param userId the ID of the user whose tasks are to be retrieved
     * @return a list of TaskMongo entities linked to the provided user ID
     */
    List<TaskMongo> findByUserId(String userId);

    /**
     * Deletes all TaskMongo entities associated with a specified user ID.
     *
     * @param userId the ID of the user whose tasks should be deleted
     */
    void deleteAllByUserId(String userId);
}
