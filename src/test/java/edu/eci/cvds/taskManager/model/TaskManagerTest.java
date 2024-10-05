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
    void addTask_ShouldAddTaskToManager() {
        Task task = new Task("1", "Test task", false,  "MEDIUM", 3, 5);

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
    void addTask_ShouldOverwriteExistingTaskWithSameId() {
        Task task1 = new Task("1", "Test task 1", false,  "LOW", 1, 0);
        Task task2 = new Task("1", "Test task 2", true,  "HIGH", 3, 5);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task retrievedTask = taskManager.getTask("1");
        assertNotNull(retrievedTask, "Task should be retrieved after adding");
        assertEquals(task2, retrievedTask, "Retrieved task should be the second task added");
        assertEquals("Test task 2", retrievedTask.getDescription(), "Task description should be from the second task");
        assertTrue(retrievedTask.isCompleted(), "Task should be marked as completed (from second task)");
        assertEquals("HIGH", retrievedTask.getDifficultyLevel(), "Difficulty level should be from the second task");
        assertEquals(3, retrievedTask.getPriority(), "Priority should be from the second task");
        assertEquals(5, retrievedTask.getAverageDevelopmentTime(), "Average development time should be from the second task");

    }

    /*
     * Verify adding a new task increments de task count.
     */
    @Test
    void addTask_ShouldIncreaseTaskCount() {
        Task task1 = new Task("1", "Test task 1", false,  "LOW", 1, 0);
        Task task2 = new Task("2", "Test task 2", false,  "LOW", 1, 0);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertEquals(2, taskManager.getAllTasks().size(), "Task count should be 2 after adding two tasks");
    }

    /*
     * Verify not to allow adding a null Task.
     */
    @Test
    void addTask_ShouldThrowExceptionForNullTask() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(null),
                "Adding null task should throw IllegalArgumentException");
    }

    /*
     * Verify not allow add a Task with null ID.
     */
    @Test
    void addTask_ShouldThrowExceptionForTaskWithNullId() {
        Task taskWithNullId = new Task(null, "Test task", false,  "LOW", 1, 0);
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(taskWithNullId),
                "Adding task with null ID should throw IllegalArgumentException");
    }

    /*
     * Verify adding a task with invalid priority throws an exception.
     */
    @Test
    void addTask_ShouldThrowExceptionForInvalidPriority() {
        Task taskWithInvalidPriority = new Task("3", "Test task with invalid priority", false, "MEDIUM", 6, 5);
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(taskWithInvalidPriority),
                "Adding task with priority greater than 5 should throw IllegalArgumentException");
        Task task1 = new Task("3", "Test task with invalid priority", false, "MEDIUM", -1, 5);
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(task1),
                "Adding task with priority greater than 5 should throw IllegalArgumentException");
    }

    /*
     * Verify adding a task with negative average development time throws an exception.
     */
    @Test
    void addTask_ShouldThrowExceptionForNegativeAverageDevelopmentTime() {
        Task taskWithNegativeTime = new Task("4", "Test task with negative time", false, "LOW", 1, -1);
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(taskWithNegativeTime),
                "Adding task with negative average development time should throw IllegalArgumentException");
    }
}