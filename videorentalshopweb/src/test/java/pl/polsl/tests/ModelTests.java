/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.model.ModelLogic;
import pl.polsl.model.Movie;
import pl.polsl.model.User;

/**
 * Unit tests for the ModelLogic class.
 * 
 * This class contains parameterized tests for various methods in the ModelLogic class,
 * covering scenarios such as successful operations, invalid inputs, and boundary cases.
 * Each test is designed to ensure the proper functionality of the corresponding ModelLogic method.
 * 
 * @author Michal Lajczak
 * @version 1.4
 */
public class ModelTests {
    
    /** The instance of the ModelLogic class used for testing. */
    private ModelLogic modelLogic;
    
    /**
     * Sets up the testing environment before each test.
     */
    @BeforeEach
    public void setUp() {
        modelLogic = ModelLogic.getInstance();
    }
    
    /**
     * Tests the createUser method of the ModelLogic class.
     * 
     * This parameterized test covers scenarios such as creating a new user with valid inputs,
     * attempting to create a user with an existing username, providing an empty username, 
     * using a short password, and mismatched password confirmation.
     * 
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param cpassword The confirmation password for the new user.
     * @param expectedStatus The expected status message from the createUser method.
     */
    @ParameterizedTest
    @CsvSource({"newUser, password123, password123, Success",
                "existingUser, password123, password123, That username is taken",
                "'', password123, password123, Username field can not be empty!",
                "newUser2, pass, pass, Password must have at least 8 characters",
                "newUser2, password123, wrongPassword, Password and Confirm Password do not match!"})
    public void createUser(String username, String password, String cpassword, String expectedStatus) {
        String status = modelLogic.createUser(username, password, cpassword);
        assertEquals(expectedStatus, status);
    }

    /**
     * Tests the loginUser method of the ModelLogic class.
     * 
     * This parameterized test covers scenarios such as logging in with valid credentials,
     * providing an empty username, an empty password, a non-existing username,
     * and using an invalid password.
     * 
     * @param username The username for login.
     * @param password The password for login.
     * @param cpassword The confirmation password for login.
     * @param expectedStatus The expected status message from the loginUser method.
     */
    @ParameterizedTest
    @CsvSource({"testUser, password123, password123, testUser",
                "testUser,'' ,'' , Enter password!",
                "'','' , password123, Enter username!",
                "nonexistentUser, password123,'' , User does not exist!",
                "testUser, wrongPassword,'' , Invalid password!"})
    public void loginUser(String username, String password, String cpassword, String expectedStatus) {
        modelLogic.createUser("testUser", "password123", "password123");
        String status = modelLogic.loginUser(username, password);
        assertEquals(expectedStatus, status);
    }

    /**
     * Tests the findUserByName method of the ModelLogic class.
     * 
     * This parameterized test covers scenarios such as finding an existing user by username
     * and attempting to find a non-existing user by username.
     * 
     * @param username The username to search for.
     * @param shouldExist A boolean indicating whether the user should exist.
     */
    @ParameterizedTest
    @CsvSource({"existingUser, true", "nonexistentUser, false"})
    public void findUserByName(String username, boolean shouldExist) {
        if (shouldExist) {
            modelLogic.createUser("existingUser", "password123", "password123");
        }

        User foundUser = modelLogic.findUserByName(username);
        assertEquals(shouldExist, foundUser != null);
    }

    /**
     * Tests the findMovieByTitle method of the ModelLogic class.
     * 
     * This parameterized test covers scenarios such as finding a movie by its title.
     * 
     * @param title The title of the movie to search for.
     * @param year The year of the movie.
     * @param price The price of the movie.
     */
    @ParameterizedTest
    @CsvSource({"Avatar, 2009, 20", "Inception, 2010, 15"})
    public void findMovieByTitle(String title, int year, int price) {
        Movie testMovie = new Movie();
        testMovie.setTitle(title);
        testMovie.setYear(year);
        testMovie.setPrice(price);

        modelLogic.getMovies().add(testMovie);

        Movie foundMovie = modelLogic.findMovieByTitle(title);
        assertNotNull(foundMovie);
        assertEquals(price, foundMovie.getPrice());
    }

    /**
     * Tests the buyPremium method of the ModelLogic class.
     * 
     * This parameterized test covers scenarios such as buying premium with sufficient funds,
     * and attempting to buy premium with insufficient funds.
     * 
     * @param username The username for which to buy premium.
     * @param initialMoney The initial money of the user.
     * @param expectedStatus The expected status message from the buyPremium method.
     */
    @ParameterizedTest
    @CsvSource({"existingUser, 60, You are premium user now!",
                "existingUser2, 10, You do not have enough money to buy premium"})
    public void buyPremium(String username, int initialMoney, String expectedStatus) {
        modelLogic.createUser(username, "password123", "password123");
        User testUser = modelLogic.findUserByName(username);
        testUser.setMoney(initialMoney);

        String status = modelLogic.buyPremium(testUser);

        assertEquals(expectedStatus, status);
        if (expectedStatus.equals("You are premium user now!")) {
            assertTrue(testUser.isPremium());
        }
    }

    /**
     * Tests the buyMovie method of the ModelLogic class.
     * 
     * This parameterized test covers scenarios such as successfully buying a movie,
     * attempting to buy a movie with insufficient funds, and attempting to buy a movie already owned.
     * 
     * @param username The username for which to buy the movie.
     * @param movieTitle The title of the movie to buy.
     * @param initialMoney The initial money of the user.
     * @param expectedStatus The expected status message from the buyMovie method.
     */
    @ParameterizedTest
    @CsvSource({"existingUser, Avatar, 30, Success",
                "existingUser, Inception, 100, You do not have enough money to buy that movie!",
                "existingUser, Avatar, 100, You have that movie!"})
    public void buyMovie(String username, String movieTitle, int initialMoney, String expectedStatus) {
        modelLogic.createUser(username, "password123", "password123");
        Movie testMovie = new Movie();
        testMovie.setTitle(movieTitle);
        testMovie.setPrice(initialMoney);
        
        User testUser = modelLogic.findUserByName(username);
        testUser.setMoney(30);

        String status = modelLogic.buyMovie(testUser, testMovie);

        assertEquals(expectedStatus, status);
        if (expectedStatus.equals("Success")) {
            assertTrue(testUser.getUserMovies().contains(testMovie));
        }
    }
}
