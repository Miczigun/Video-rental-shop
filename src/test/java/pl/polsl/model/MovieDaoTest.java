/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.model;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the MovieDao class.
 * @author Michal Lajczak
 * @version 1.2
*/
public class MovieDaoTest {

    private MovieDao movieDao;

    /**
     * Set up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        movieDao = new MovieDao();
    }

    /**
     * That test is commented because operating on DML
     * Parameterized test for the addMovie method.
     *
     * @param movie The movie to be added to the database.
     */
    /*
    @ParameterizedTest
    @ValueSource(strings = {"Movie1", "Movie2", "Movie3"})
    public void testAddMovie(String movieName) {
        Movie movie = new Movie();
        movie.setTitle(movieName);

        movieDao.addMovie(movie);

        // Check if the movie was added successfully
        assertNotNull(movie.getId(), "Movie ID should not be null after adding to the database");
    }
    */
    
    /**
     * Parameterized test for the getMovieById method.
     *
     * @param movieId The ID of the movie to be retrieved from the database.
     */
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    public void testGetMovieById(long movieId) {
        Movie result = movieDao.getMovieById(movieId);

        // Check if the retrieved movie has the expected ID
        assertNotNull(result, "Expected a non-null result for movie ID: " + movieId);
        assertEquals(movieId, result.getId(), "Unexpected movie ID");
    }

    /**
     * That test is commented because operating on DML
     * Parameterized test for the deleteMovie method.
     *
     * @param movieId The ID of the movie to be deleted from the database.
     */
    /*
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    public void testDeleteMovie(long movieId) {
        movieDao.deleteMovie(movieId);

        // Check if the movie was deleted successfully
        Movie deletedMovie = movieDao.getMovieById(movieId);
        assertNull(deletedMovie, "Movie should be null after deletion from the database");
    }
    */
    
    /**
     * Parameterized test for the updateMoviePrice method.
     *
     * @param movieId The ID of the movie to be updated.
     * @param price   The new price for the movie.
     */
    @ParameterizedTest
    @CsvSource({"1, 15", "2, 20", "3, 25"})
    public void testUpdateMoviePrice(long movieId, int price) {
        movieDao.updateMoviePrice(movieId, price);

        // Check if the movie was updated successfully
        Movie updatedMovie = movieDao.getMovieById(movieId);
        assertNotNull(updatedMovie, "Expected a non-null result for updated movie ID: " + movieId);
        assertEquals(price, updatedMovie.getPrice(), "Unexpected movie price after update");
    }

    /**
     * Test for the getAllMovies method.
     */
    @Test
    public void testGetAllMovies() {
        List<Movie> movies = movieDao.getAllMovies();

        // Check if the list is not empty
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }

    /**
     * Parameterized test for the getMoviesByGenre method.
     *
     * @param genreName The name of the genre to filter movies.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Action", "Comedy", "Drama"})
    public void testGetMoviesByGenre(String genreName) {
        List<Movie> movies = movieDao.getMoviesByGenre(genreName);

        // Check if the list is not empty
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
    }
}
