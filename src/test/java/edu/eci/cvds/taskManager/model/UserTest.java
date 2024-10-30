package edu.eci.cvds.taskManager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    
    private User testUser1;
    private User testUser2;
    private User testUser3;

    /*
     * Set up testUser instance before each test.
     */
    @BeforeEach
    public void setUp() {
        testUser1 = new User();
        testUser2 = new User("Sergio", "deleop01");
        testUser3 = new User("Laura", "123");
    }

    /**
     * Tests the default constructor of the {@link User} class.
     * Ensures that a new User object has null values for id, username, password, and roleId.
     */
    @Test
    void testDefaultConstructor() {
        assertNull(testUser1.getId());
        assertNull(testUser1.getUsername());
        assertNull(testUser1.getPassword());
        assertNull(testUser1.getRoleId());
    }

    /**
     * Tests the parameterized constructor of the {@link User} class.
     * Verifies that the constructor correctly sets the username and password
     * and that the id and roleId fields remain null.
     */
    @Test
    public void testParameterizedConstructor() {
        assertEquals("Sergio", testUser2.getUsername());
        assertEquals("deleop01", testUser2.getPassword());
        assertNull(testUser2.getId());
        assertNull(testUser2.getRoleId());

        assertEquals("Laura", testUser3.getUsername());
        assertEquals("123", testUser3.getPassword());
        assertNull(testUser3.getId());
        assertNull(testUser3.getRoleId());
    }

    /**
     * Tests the {@code setId} method of the {@link User} class.
     * Ensures that the id field is correctly updated when using the setter.
     */
    @Test
    public void testSetId() {
        testUser1.setId("testId1");
        assertEquals("testId1", testUser1.getId());

        testUser2.setId("testId2");
        assertEquals("testId2", testUser2.getId());
    }

    /**
     * Tests the {@code setUsername} method of the {@link User} class.
     * Ensures that the username field is correctly updated when using the setter.
     */
    @Test
    public void testSetUsername() {
        testUser1.setUsername("Juan");
        assertEquals("Juan", testUser1.getUsername());
    }

    /**
     * Tests the {@code setPassword} method of the {@link User} class.
     * Ensures that the password field is correctly updated when using the setter.
     */
    @Test
    public void testSetPassword() {
        testUser1.setPassword("develop");
        assertEquals("develop", testUser1.getPassword());
    }

    /**
     * Tests the {@code setRoleId} method of the {@link User} class.
     * Ensures that the roleId field is correctly updated when using the setter.
     */
    @Test
    public void testSetRoleId() {
        testUser1.setRoleId("1");
        assertEquals("1", testUser1.getRoleId());

        testUser3.setRoleId("2");
        assertEquals("2", testUser3.getRoleId());
    }

    /**
     * Tests that different instances of {@link User} maintain independent states.
     * Ensures that modifications to one instance do not affect other instances.
     */
    @Test
    public void testIndependentInstances() {
        testUser1.setUsername("user1");
        testUser2.setUsername("user2");

        assertNotEquals(testUser1.getUsername(), testUser2.getUsername());
    }
}
