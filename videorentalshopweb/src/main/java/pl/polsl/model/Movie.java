/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Represents a movie entity with various attributes.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
@Data
@Entity
public class Movie {

    /**
     * The unique identifier for the movie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The title of the movie.
     */
    private String title;

    /**
     * The price of the movie.
     */
    private int price;

    /**
     * The release year of the movie.
     */
    @Column(columnDefinition = "YEAR")
    private int year;

    /**
     * The genre of the movie.
     */
    @ManyToOne
    @JoinColumn(name = "genre")
    private String genre;
    
    
//    Code for JPA
    
//    @ManyToMany(mappedBy = "movies")
//    private List<User> users;   
//    /**
//     * Retrieves the name of the genre associated with the movie.
//     * 
//     * @return The name of the genre.
//     */
//    public String getGenre(){
//        return genre.getName();
//    }


}
