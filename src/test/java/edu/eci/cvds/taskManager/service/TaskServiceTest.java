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

    private Task task;
    private TaskMongo taskMongo;
    private TaskPostgres taskPostgres;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        task = new Task("Test Task");
        taskMongo = new TaskMongo(task);
        taskPostgres = new TaskPostgres(task);
    }

    @Test
    public void testSaveTaskSuccessWithOneTaskRegistered() {
        when(taskMongoRepository.save(any(TaskMongo.class))).thenReturn(taskMongo);

        Task result = taskService.save(task);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
    }

    @Test
    public void testFindTaskByIdNoTask() {
        when(taskMongoRepository.findById(task.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            taskService.markAsCompleted(task.getId());
        });

        verify(taskMongoRepository, times(1)).findById(task.getId());
    }

    @Test
    public void testSaveTaskSuccess() throws Exception {
        when(taskMongoRepository.save(any(TaskMongo.class))).thenReturn(taskMongo);

        doNothing().when(taskPostgresRepository).save(any(TaskPostgres.class));

        Task result = taskService.save(task);

        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        verify(taskMongoRepository, times(1)).save(any(TaskMongo.class));
        verify(taskPostgresRepository, times(1)).save(any(TaskPostgres.class));
    }

    @Test
    public void testDeleteTaskSuccess() throws Exception {
        doNothing().when(taskMongoRepository).deleteById(task.getId());
        doNothing().when(taskPostgresRepository).deleteById(task.getId());

        taskService.deleteById(task.getId());

        verify(taskMongoRepository, times(1)).deleteById(task.getId());
        verify(taskPostgresRepository, times(1)).deleteById(task.getId());
    }

    @Test
    public void testDeleteAndFindTaskNoTask() throws Exception {
        doNothing().when(taskMongoRepository).deleteById(task.getId());
        doNothing().when(taskPostgresRepository).deleteById(task.getId());
        when(taskMongoRepository.findById(task.getId())).thenReturn(Optional.empty());

        taskService.deleteById(task.getId());

        assertThrows(RuntimeException.class, () -> {
            taskService.markAsCompleted(task.getId());
        });

        verify(taskMongoRepository, times(1)).deleteById(task.getId());
        verify(taskPostgresRepository, times(1)).deleteById(task.getId());
        verify(taskMongoRepository, times(1)).findById(task.getId());
    }
}
