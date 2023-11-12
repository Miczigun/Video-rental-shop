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
public class GenreModelTest {

    /**
     * Test case to ensure that the constructor initializes fields correctly.
     */
    @Test
    void constructor_ShouldInitializeFields() {
        Genre genre = new Genre();
        assertNotNull(genre);
        assertEquals(0, genre.getId()); // Assuming default value is 0 for an uninitialized id
        assertNull(genre.getName());
    }    
}
