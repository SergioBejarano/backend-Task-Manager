package edu.eci.cvds.taskManager.repositories;

import edu.eci.cvds.taskManager.model.TaskPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The TaskPostgresRepository interface provides the data access layer for Task entities in PostgreSQL.
 * It extends the JpaRepository interface, which provides basic CRUD operations for relational databases.
 */
@Repository
public interface TaskPostgresRepository extends JpaRepository<TaskPostgres, String> {
    // No additional methods are defined; the default CRUD operations are inherited from JpaRepository.
}
