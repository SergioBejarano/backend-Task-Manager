package edu.eci.cvds.taskManager.repositories.postgres;
import edu.eci.cvds.taskManager.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}