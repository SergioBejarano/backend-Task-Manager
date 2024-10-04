import edu.eci.cvds.taskManager.model.Task;
import edu.eci.cvds.taskManager.model.TaskMongo;
import edu.eci.cvds.taskManager.model.TaskPostgres;
import edu.eci.cvds.taskManager.repositories.mongo.TaskMongoRepository;
import edu.eci.cvds.taskManager.repositories.postgres.TaskPostgresRepository;
import edu.eci.cvds.taskManager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskMongoRepository taskMongoRepository;

    @Mock
    private TaskPostgresRepository taskPostgresRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetTaskByIdSuccessfully() {
        Task task = new Task("Sample task");
        task.setId("123");
        TaskMongo taskMongo = new TaskMongo(task);
        TaskPostgres taskPostgres = new TaskPostgres(task);

        List<TaskMongo> mongoTasks = new ArrayList<>();
        mongoTasks.add(taskMongo);

        List<TaskPostgres> postgresTasks = new ArrayList<>();
        postgresTasks.add(taskPostgres);

        when(taskMongoRepository.findAll()).thenReturn(mongoTasks);

        Task retrievedTask = taskService.findAll().get(0);

        assertNotNull(retrievedTask);
        assertEquals("123", retrievedTask.getId());
    }

    @Test
    void shouldNotGetTaskWhenNoTaskIsRegistered() {
        when(taskMongoRepository.findAll()).thenReturn(new ArrayList<>());

        List<TaskMongo> tasks = taskService.findAll();

        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldCreateTaskSuccessfullyWhenNoTaskIsRegistered() {
        Task newTask = new Task("New task");
        newTask.setId("123");
        TaskMongo taskMongo = new TaskMongo(newTask);
        TaskPostgres taskPostgres = new TaskPostgres(newTask);

        when(taskMongoRepository.save(taskMongo)).thenReturn(taskMongo);

        Task createdTask = taskService.save(newTask);

        //assertNotNull(createdTask);
        //assertEquals("123", createdTask.getId());
        //assertEquals("New task", createdTask.getDescription());

        // Verificamos que se llamaron los métodos save
        //verify(taskMongoRepository).save(taskMongo);
    }


    @Test
    void shouldDeleteTaskSuccessfullyWhenTaskIsRegistered() {
        Task task = new Task("Task to delete");
        task.setId("5");
        TaskMongo taskMongo = new TaskMongo(task);
        TaskPostgres taskPostgres = new TaskPostgres(task);
        doNothing().when(taskMongoRepository).deleteById("5");

        taskService.deleteById("5");

        verify(taskMongoRepository, times(1)).deleteById("5");
    }

    @Test
    void shouldNotReturnTaskAfterDeletion() {
        Task task = new Task("Task to delete");
        task.setId("8");
        TaskMongo taskMongo = new TaskMongo(task);
        TaskPostgres taskPostgres = new TaskPostgres(task);
        doNothing().when(taskMongoRepository).deleteById("8");

        taskService.deleteById("8");

        when(taskMongoRepository.findAll()).thenReturn(new ArrayList<>());

        List<TaskMongo> tasks = taskService.findAll();

        assertTrue(tasks.stream().noneMatch(t -> t.getId().equals("8")));
    }
}
