/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.model;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the GenreDao class.
 * @author Miczi
 * @version 1.2
 */
public class GenreDaoTest {
    
    private GenreDao genreDao;

    /**
     * Set up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        genreDao = new GenreDao();
    }

    /**
     * Parameterized test for the getGenre method.
     *
     * @param genreId The ID of the genre to be used for testing.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, -1, 0})
    public void testGetGenre(int genreId) {
        Genre result = genreDao.getGenre(genreId);

        if (genreId > 0) {
            // Valid ID - expect a Genre object
            assertNotNull(result, "Expected a non-null result for genre ID: " + genreId);
            assertEquals(genreId, result.getId(), "Unexpected genre ID");
        } else {
            // Invalid or boundary ID - expect null
            assertNull(result, "Expected null result for genre ID: " + genreId);
        }
    }

    /**
     * Parameterized test for the getGenres method.
     *
     * @param genreName The name of the genre to be used for testing.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Action", "Comedy", "Drama", "Thriller"})
    public void testGetGenres(String genreName) {
        // Prepare the database with test data
        // ...

        List<String> genres = genreDao.getGenres();
        assertNotNull(genres);

        // Check if the list is not empty
        assertFalse(genres.isEmpty());

        // Additional tests for valid, invalid, and boundary cases
        // e.g., check if returned names are not empty, if the name format is correct, etc.
        assertTrue(genres.contains(genreName), "Expected genre not found: " + genreName);
    }
}
