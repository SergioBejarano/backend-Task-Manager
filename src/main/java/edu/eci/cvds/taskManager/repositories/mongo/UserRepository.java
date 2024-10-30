package edu.eci.cvds.taskManager.repositories.mongo;
import edu.eci.cvds.taskManager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The UserRepository interface provides the data access layer for User entities.
 * It extends the MongoRepository interface, which provides basic CRUD operations.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the found user, or empty if no user found
     */
    Optional<User> findByUsername(String username);
}