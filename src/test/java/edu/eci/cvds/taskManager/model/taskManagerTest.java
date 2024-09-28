package edu.eci.cvds.taskManager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    /* 
     * Set up TaskManager instance before each test.
     */
    @BeforeEach
    public void setUp() {
        taskManager = new TaskManager();
    }

    /* 
     * Verify adding a task successfully to the TaskManager.
     */
    @Test
    public void addTask_ShouldAddTaskToManager() {
        Task task = new Task("1", "Test task", false);

        taskManager.addTask(task);

        Task retrievedTask = taskManager.getTask("1");
        assertNotNull(retrievedTask, "Task should be retrieved after adding");
        assertEquals(task, retrievedTask, "Retrieved task should be the same as the added task");
    }

    /* 
     * Verify adding a task with the same ID 
     * will overwrite the existing task.
     */
    @Test
    public void addTask_ShouldOverwriteExistingTaskWithSameId() {
        Task task1 = new Task("1", "Test task 1", false);
        Task task2 = new Task("1", "Test task 2", true);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task retrievedTask = taskManager.getTask("1");
        assertNotNull(retrievedTask, "Task should be retrieved after adding");
        assertEquals(task2, retrievedTask, "Retrieved task should be the second task added");
        assertEquals("Test task 2", retrievedTask.getDescription(), "Task description should be from the second task");
        assertTrue(retrievedTask.isCompleted(), "Task should be marked as completed (from second task)");
    }

    /* 
     * Verify adding a new task increments de task count.
     */
    @Test
    public void addTask_ShouldIncreaseTaskCount() {
        Task task1 = new Task("1", "Test task 1", false);
        Task task2 = new Task("2", "Test task 2", false);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertEquals(2, taskManager.getAllTasks().size(), "Task count should be 2 after adding two tasks");
    }

    /* 
     * Verify not to allow adding a null Task.
     */
    @Test
    public void addTask_ShouldThrowExceptionForNullTask() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(null),
                "Adding null task should throw IllegalArgumentException");
    }

    /* 
     * Verify not allow add a Task with null ID.
     */
    @Test
    public void addTask_ShouldThrowExceptionForTaskWithNullId() {
        Task taskWithNullId = new Task(null, "Test task", false);
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(taskWithNullId),
                "Adding task with null ID should throw IllegalArgumentException");
    }
}