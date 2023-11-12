/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Michal Lajczak
 * @version 1.1
 */
public class MovieModelTest {
    
    /**
     * Test case to verify that the getGenre method returns the correct genre name.
     */
    @Test
    void getGenre_ShouldReturnGenreName() {
        Genre genre = new Genre();
        genre.setName("Action");

        Movie movie = new Movie();
        movie.setGenre(genre);

        assertEquals("Action", movie.getGenre());
    }

    /**
     * Test case to ensure that the constructor initializes fields correctly.
     */
    @Test
    void constructor_ShouldInitializeFields() {
        Movie movie = new Movie();
        assertNotNull(movie);
        assertEquals(0, movie.getId()); // Assuming default value is 0 for an uninitialized id
        assertNull(movie.getTitle());
        assertEquals(0, movie.getPrice());
        assertEquals(0.0, movie.getRating(), 0.01); // Assuming default value is 0.0 for an uninitialized rating
        assertEquals(0, movie.getYear());
        assertNull(movie.getGenre());
    }
    
}
