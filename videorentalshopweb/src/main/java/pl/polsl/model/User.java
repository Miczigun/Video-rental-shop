/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Data;

/**
 * Represents a user entity with various attributes.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
@Data
@Entity
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The hashed password of the user, using the Bcrypt function.
     */
    private String password;  

    /**
     * Indicates whether the user has a premium account.
     */
    private boolean premium = false;

    /**
     * The balance in the user's account.
     */
    private double balance;

    /**
     * The list of movies associated with the user, indicating their preferences.
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_movies",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;
    
    private boolean admin;

    /**
     * Sets the password using the Bcrypt hashing function.
     * 
     * @param password The plain text password to be hashed.
     */
    public void setPassword(String password){
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());    
    }

    /**
     * Retrieves the premium status of the user.
     * 
     * @return True if the user has a premium account, false otherwise.
     */
    public boolean getPremium(){
        return this.premium;
    }
    
    public void addMovie(Movie movie){
        this.movies.add(movie);
        movie.getUsers().add(this);
    }
    
    public void deleteMovie(Movie movie){
        this.movies.remove(movie);
        movie.getUsers().remove(this);
    }
}

