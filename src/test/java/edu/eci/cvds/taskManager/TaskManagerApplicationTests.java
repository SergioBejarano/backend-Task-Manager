package edu.eci.cvds.taskManager;

import edu.eci.cvds.taskManager.model.*;
import edu.eci.cvds.taskManager.model.Task.DifficultyLevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerApplicationTests {

	private TaskManager taskManager;

	@BeforeEach
	void setUp() {
		taskManager = new TaskManager();
	}

	@Test
	void shouldCreateATask() {
		Task task = new Task("Test task");
		assertEquals("Test task", task.getDescription());
		assertFalse(task.isCompleted());
	}

	@Test
	void shouldCreateATaskWithoutDescription() {
		Task task = new Task();
		assertNull(task.getDescription());
		assertFalse(task.isCompleted());
	}

	@Test
	void shouldSetAId() {
		Task task = new Task("Sample task");
		task.setId("12345");
		assertEquals("12345", task.getId());
	}

	@Test
	void shouldSetADescription() {
		Task task = new Task();
		task.setDescription("New task description");
		assertEquals("New task description", task.getDescription());
	}

	@Test
	void shouldSetCompleted() {
		Task task = new Task("Complete this");
		task.setCompleted(true);
		assertTrue(task.isCompleted());
	}

	@Test
	void shouldSetNotCompleted() {
		Task task = new Task("No complete this");
		task.setCompleted(false);
		assertFalse(task.isCompleted());
	}

	@Test
	void shouldAddTask() {
		Task task = new Task("Test task");
		task.setId("1");
		taskManager.addTask(task);
		assertEquals(task, taskManager.getTask("1"));
	}

	@Test
	void shouldGetTask() {
		Task task = new Task("Get this task");
		task.setId("2");
		taskManager.addTask(task);
		Task retrievedTask = taskManager.getTask("2");
		assertNotNull(retrievedTask);
		assertEquals("Get this task", retrievedTask.getDescription());
	}

	@Test
	void shouldNotGetTask() {
		Task task = new Task("Get this task");
		task.setId("2");
		taskManager.addTask(task);
		Task retrievedTask = taskManager.getTask(null);
		assertNull(retrievedTask);
	}

	@Test
	void shouldRemoveTask() {
		Task task = new Task("Delete this task");
		task.setId("3");
		taskManager.addTask(task);
		taskManager.deleteTask("3");
		assertNull(taskManager.getTask("3"));
	}

	@Test
	void shouldNotRemoveTask() {
		Task task = new Task("Delete this task");
		task.setId("3");
		taskManager.addTask(task);
		taskManager.deleteTask(null);
		assertNotNull(taskManager.getTask("3"));
	}

	@Test
	void shouldMarkAsCompleted() {
		Task task = new Task("Complete this task");
		task.setId("4");
		taskManager.addTask(task);
		taskManager.markAsCompleted("4");
		assertTrue(taskManager.getTask("4").isCompleted());
	}

	@Test
	void shouldNotMarkAsCompleted(){
		Task eTask = new Task("Complete this task");
		eTask.setId("4");
		taskManager.addTask(eTask);
		taskManager.markAsCompleted(null);
		ArrayList<Task> tasks = new ArrayList<>(taskManager.getAllTasks().values());
		for (Task task : tasks){
			assertFalse(task.isCompleted());
		}
	}

	@Test
	void shouldGetAllTasks() {
		Task task1 = new Task("Task 1");
		task1.setId("5");
		Task task2 = new Task("Task 2");
		task2.setId("6");
		taskManager.addTask(task1);
		taskManager.addTask(task2);
		assertEquals(2, taskManager.getAllTasks().size());
	}

	@Test
	void shouldSetDifficultyLevel() {
		Task task1 = new Task("Task 1");
		task1.setId("7");
		task1.setDifficultyLevel(DifficultyLevel.MEDIUM);
		taskManager.addTask(task1);
		assertEquals(DifficultyLevel.MEDIUM, taskManager.getTask("7").getDifficultyLevel());
	}

	@Test
	void shouldSetPriority() {
		Task task1 = new Task("Task 1");
		task1.setId("7");
		task1.setPriority(5);
		taskManager.addTask(task1);
		assertEquals(5, taskManager.getTask("7").getPriority());
	}

	@Test
	void shouldNotSetPriority() {
		Task task1 = new Task("Task 1");
		task1.setId("7");
		assertThrows(IllegalArgumentException.class, () -> task1.setPriority(-1),
                "Adding task with null ID should throw IllegalArgumentException");
		assertThrows(IllegalArgumentException.class, () -> task1.setPriority(10),
		"Adding task with null ID should throw IllegalArgumentException");
	}

	@Test
	void shouldSetAverageDevelopmentTime() {
		Task task1 = new Task("Task 1");
		task1.setId("7");
		task1.setAverageDevelopmentTime(10);
		taskManager.addTask(task1);
		assertEquals(10, taskManager.getTask("7").getAverageDevelopmentTime());
	}

	@Test
	void shouldNotSetAverageDevelopmentTime() {
		Task task1 = new Task("Task 1");
		task1.setId("7");
		assertThrows(IllegalArgumentException.class, () -> task1.setAverageDevelopmentTime(-1),
                "Adding task with null ID should throw IllegalArgumentException");
	}
}