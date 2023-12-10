/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import lombok.Data;

/**
 * Represents a movie in the system.
 *
 * This class encapsulates information about a movie, including its title, genre, release year,
 * and price. The class uses Lombok annotations for automatic generation of getters, setters, and other
 * boilerplate code.
 *
 * @author Michal Lajczak
 * @version 1.4
 */
@Data
public class Movie {
    /** The title of the movie. */
    private String title;

    /** The genre of the movie. */
    private String genre;

    /** The release year of the movie. */
    private int year;

    /** The price of the movie. */
    private int price;
}

