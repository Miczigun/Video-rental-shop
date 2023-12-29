/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.model.Dao.UserDao;
import pl.polsl.model.User;

/**
 * JUnit 5 test class for the UserDao class
 * @author Michal Lajczak
 * @version 1.5
 */
public class UserDaoTest {
    
    private UserDao userDao;
    
    @BeforeEach
    public void setUp() {
        userDao = new UserDao();
    }
    
    /**
     * Parameterized test for the addUser method with various scenarios.
     *
     * @param username the username to be added
     * @param password the password to be added
     * @param premium the premium status to be added
     * @param balance the balance to be added
     * @param admin the admin status to be added
     * @param expected the expected result of the addUser method
     */
    @ParameterizedTest
    @CsvSource({
            // Successful case
            "testUser, password, true, 100, true, true",
            // User already exists
            "existingUser, password, false, 0, false, false",
            // Empty username
            "'', password, false, 0, false, false",
            // Incorrect password length
            "newUser, short, false, 0, false, false",
            // Null user
            "'','',false,0,false,false"
    })
    void testAddUser(String username, String password, boolean premium, int balance, boolean admin, boolean expected) {
        // Arrange
        User user = new User(username, password);
        user.setPremium(premium);
        user.setBalance(balance);
        user.setAdmin(admin);

        // Act
        boolean result = userDao.addUser(user);

        // Assert
        assertEquals(expected, result, "addUser should return the expected result");

        // Additional assertions based on the result
        if (expected) {
            // If the user was added successfully, assert that the user exists in the database
            User addedUser = userDao.getUserByUsername(username);
            assertNotNull(addedUser, "User should be retrieved after successful addition");
            assertEquals(username, addedUser.getUsername(), "Retrieved user should have the correct username");
            assertEquals(premium, addedUser.getPremium(), "Retrieved user should have the correct premium status");
            assertEquals(balance, addedUser.getBalance(), "Retrieved user should have the correct balance");
            assertEquals(admin, addedUser.isAdmin(), "Retrieved user should have the correct admin status");
        } else {
            // If the addition failed, assert that the user does not exist in the database
            User nonExistentUser = userDao.getUserByUsername(username);
            assertNull(nonExistentUser, "User should not exist in the database");
        }
    }
    
    /**
     * Parameterized test for the registerUser method with various scenarios.
     *
     * @param existingUsername an existing username in the database
     * @param username the username to be registered
     * @param password the password to be registered
     * @param cPassword the confirmation password for registration
     * @param expectedStatus the expected status message from the registerUser method
     */
    @ParameterizedTest
    @CsvSource({
            // Successful registration
            "existingUser, newUser, password, password, Success",
            // Username already taken
            "existingUser, existingUser, password, password, That username is taken",
            // Empty username
            "existingUser, '', password, password, Username field can not be empty!",
            // Password too short
            "existingUser, newUser, short, short, Password must have a minimum of 8 characters",
            // Password and Confirm password mismatch
            "existingUser, newUser, password, differentPassword, Password and Confirm password have to be the same!"
    })
    void testRegisterUser(String existingUsername, String username, String password, String cPassword, String expectedStatus) {
        // Arrange
        userDao.addUser(new User(existingUsername, "existingPassword")); // Add an existing user

        // Act
        String result = userDao.registerUser(username, password, cPassword);

        // Assert
        assertEquals(expectedStatus, result, "registerUser should return the expected result");

        // Additional assertions based on the result
        if ("Success".equals(expectedStatus)) {
            // If registration is successful, assert that the user now exists in the database
            assertNotNull(userDao.getUserByUsername(username), "User should exist in the database after successful registration");
        } else {
            // If registration failed, assert that the user does not exist in the database
            assertNull(userDao.getUserByUsername(username), "User should not exist in the database after failed registration");
        }
    }
    
    /**
     * Parameterized test for the loginUser method with various scenarios.
     *
     * @param existingUsername an existing username in the database
     * @param username the username to be used for login
     * @param password the password to be used for login
     * @param expectedStatus the expected status message from the loginUser method
     */
    @ParameterizedTest
    @CsvSource({
            // Successful login
            "existingUser, existingUser, existingPassword, existingUser",
            // Empty username
            "existingUser, '', existingPassword, Please enter your login details",
            // Empty password
            "existingUser, existingUser, '', Please enter your login details",
            // User does not exist
            "existingUser, nonExistingUser, password, User with this name does not exist",
            // Incorrect password
            "existingUser, existingUser, incorrectPassword, Incorrect password"
    })
    void testLoginUser(String existingUsername, String username, String password, String expectedStatus) {
        // Arrange
        userDao.addUser(new User(existingUsername, "existingPassword")); // Add an existing user

        // Act
        String result = userDao.loginUser(username, password);

        // Assert
        assertEquals(expectedStatus, result, "loginUser should return the expected result");

        // Additional assertions based on the result
        if (!"Please enter your login details".equals(expectedStatus)) {
            // If login is attempted, assert that the result matches the expected username
            assertEquals(username, result, "loginUser should return the correct username");
        }
    }
    
     /**
     * Parameterized test for the buyPremium method with various scenarios.
     *
     * @param existingUsername an existing username in the database
     * @param userId the ID of the user attempting to purchase premium
     * @param initialBalance the initial balance of the user
     * @param expectedStatus the expected status message from the buyPremium method
     */
    @ParameterizedTest
    @CsvSource({
            // Successful premium purchase
            "existingUser, 1, 100, You are a premium user now!",
            // User does not exist
            "existingUser, 999, 0, User does not exist",
            // User is already a premium user
            "premiumUser, 2, 50, You are a premium user!",
            // User does not have enough money
            "nonPremiumUser, 3, 40, You do not have enough money to buy premium"
    })
    void testBuyPremium(String existingUsername, int userId, int initialBalance, String expectedStatus) {
        // Arrange
        userDao.addUser(new User(existingUsername, "password", initialBalance));

        // Act
        String result = userDao.buyPremium(userId);

        // Assert
        assertEquals(expectedStatus, result, "buyPremium should return the expected result");

        // Additional assertions based on the result
        if ("You are a premium user now!".equals(expectedStatus)) {
            // If premium purchase is successful, assert that the user is now a premium user
            User premiumUser = userDao.getUserById(userId);
            assertNotNull(premiumUser, "User should exist in the database after successful premium purchase");
            assertTrue(premiumUser.getPremium(), "User should be a premium user after successful premium purchase");
            assertEquals(initialBalance - 50, premiumUser.getBalance(), "User balance should be updated after successful premium purchase");
        } else {
            // If premium purchase failed, assert that the user's premium status and balance are unchanged
            User user = userDao.getUserById(userId);
            assertNotNull(user, "User should exist in the database after premium purchase attempt");
            assertEquals(initialBalance, user.getBalance(), "User balance should not be changed after failed premium purchase");
            assertEquals(existingUsername.equals("premiumUser"), user.getPremium(), "User premium status should not be changed after failed premium purchase");
        }
    }
    
    /**
     * Parameterized test for the topUpAccount method with various scenarios.
     *
     * @param existingUsername an existing username in the database
     * @param userId the ID of the user whose account is being topped up
     * @param initialBalance the initial balance of the user
     * @param amount the amount to be added to the user's account balance
     * @param expectedBalance the expected balance of the user after the top-up
     */
    @ParameterizedTest
    @CsvSource({
            // Successful account top-up
            "existingUser, 1, 100, 50, 150",
            // User does not exist
            "existingUser, 999, 0, 50, 0",
            // Negative amount (should be ignored)
            "existingUser, 2, 50, -10, 50",
            // Zero amount (no change expected)
            "existingUser, 3, 30, 0, 30"
    })
    void testTopUpAccount(String existingUsername, int userId, int initialBalance, int amount, int expectedBalance) {
        // Arrange
        userDao.addUser(new User(existingUsername, "password", initialBalance));

        // Act
        userDao.topUpAccount(userId, amount);

        // Assert
        assertEquals(expectedBalance, userDao.getUserById(userId).getBalance(), "topUpAccount should update the user's balance as expected");
    }
}
