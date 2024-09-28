package edu.eci.cvds.taskManager.repositories;

import edu.eci.cvds.taskManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The TaskRepositoryPostgreSQL interface provides the data access layer for Task entities.
 * It extends the JpaRepository interface, which provides basic CRUD operations for managing.
 */
public interface TaskRepositoryPostgreSQL extends JpaRepository<Task, String> {
    // No additional methods are defined; the default CRUD operations are inherited from JpaRepository.
}
