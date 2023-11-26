/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.model;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the UserDao class.
 * @author Michal Lajczak
 * @version 1.2
 */
public class UserDaoTest {

    private UserDao userDao;

    /**
     * Set up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        userDao = new UserDao();
    }
    
    /**
     * Clean up the test environment after each test.
     */
    @AfterEach
    public void tearDown() {
        User user = userDao.getUserByName("user1");
        userDao.deleteUser(user.getId());
    }
    
    /**
     * Cleanup method to be executed after all tests have run.
     * Deletes users with usernames "user2" through "user5".
     * Assumes the existence of UserDao instance and appropriate transactions.
     */
    @AfterAll
    public void tearDownAll(){
        for (int i = 2; i <= 5; i++) {
            String username = "user" + i;
            User user = userDao.getUserByName(username);
            userDao.deleteUser(user.getId());
        }
    }
    
    /**
     * Parameterized test for the createUser method.
     *
     * @param username The username of the user to be created.
     */
    @ParameterizedTest
    @ValueSource(strings = {"user1", "user2", "user3"})
    public void testCreateUser(String username) {
        userDao.createUser(username, "password");
        User user = userDao.getUserByName(username);

        assertNotNull(user, "User should not be null after creation");
        assertEquals(username, user.getUsername(), "Unexpected username");
    }

    /**
     * Test for the loginUser method.
     */
    @Test
    public void testLoginUser() {
        userDao.createUser("user1", "password");
        User loggedInUser = userDao.loginUser("user1", "password");

        assertNotNull(loggedInUser, "User should be logged in successfully");
    }

    /**
     * Parameterized test for the getUserById method.
     *
     * @param userId The ID of the user to be retrieved.
     */
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    public void testGetUserById(long userId) {
        userDao.createUser("user1", "password");
        User user = userDao.getUserById(userId);

        assertNotNull(user, "User should not be null");
        assertEquals(userId, user.getId(), "Unexpected user ID");
    }

    /**
     * Test for the getAllUsers method.
     */
    @Test
    public void testGetAllUsers() {
        userDao.createUser("user1", "password");
        userDao.createUser("user4", "password");
        userDao.createUser("user5", "password");

        List<User> users = userDao.getAllUsers();

        assertNotNull(users, "List of users should not be null");
        assertFalse(users.isEmpty(), "List of users should not be empty");
    }

    /**
     * Test for the topUpTheAccount method.
     */
    @Test
    public void testTopUpTheAccount() {
        userDao.createUser("user1", "password");
        User user = userDao.getUserByName("user1");
        double initialBalance = user.getBalance();

        userDao.topUpTheAccount(user.getId(), 50);

        user = userDao.getUserByName("user1");
        assertEquals(initialBalance + 50, user.getBalance(), "Unexpected balance after top-up");
    }

    /**
     * Test for the buyMovie method.
     */
    @Test
    public void testBuyMovie() {
        userDao.createUser("user1", "password");
        User user = userDao.getUserByName("user1");
        userDao.topUpTheAccount(user.getId(), 40);

        boolean result = userDao.buyMovie(user, 1);

        assertTrue(result, "User should be able to buy the movie");
        assertTrue(userDao.findUserMovie(user, 1), "User should own the movie after purchase");
    }

    /**
     * Test for the buyPremium method.
     */
    @Test
    public void testBuyPremium() {
        userDao.createUser("user1", "password");
        User user = userDao.getUserByName("user1");
        userDao.topUpTheAccount(user.getId(), 100);
        
        boolean result = userDao.buyPremium(user);

        assertTrue(result, "User should be able to buy premium");
        assertTrue(user.getPremium(), "User should have premium status after purchase");
    }

    /**
     * Test for the findUserMovie method.
     */
    @Test
    public void testFindUserMovie() {
        userDao.createUser("user1", "password");
        User user = userDao.getUserByName("user1");
        userDao.topUpTheAccount(user.getId(), 50);

        userDao.buyMovie(user, 1);

        assertTrue(userDao.findUserMovie(user, 1), "User should own the movie");
        assertFalse(userDao.findUserMovie(user, 2), "User should not own the movie");
    }

    /**
     * Test for the removeUserMovie method.
     */
    @Test
    public void testRemoveUserMovie() {
        userDao.createUser("user1", "password");
        User user = userDao.getUserByName("user1");
        userDao.topUpTheAccount(user.getId(), 50);

        userDao.buyMovie(user, 1);

        assertTrue(userDao.findUserMovie(user, 1), "User should own the movie before removal");

        userDao.removeUserMovie(user, 1);

        assertFalse(userDao.findUserMovie(user, 1), "User should not own the movie after removal");
    }
}
