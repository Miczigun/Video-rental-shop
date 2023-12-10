/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user in the system.
 *
 * This class encapsulates user information such as username, password, movie list, account balance,
 * and premium status. It also provides methods for setting the password, topping up the account balance.
 * 
 * @author Michal Lajczak
 * @version 1.4
 */
@Getter
@Setter
public class User {
    /** The username of the user. */
    private String username;

    /** The hashed password of the user. */
    private String password;

    /** The list of movies owned by the user. */
    private List<Movie> userMovies;

    /** The account balance of the user. */
    private int money;

    /** Indicates whether the user has premium status. */
    private boolean premium;

    /**
     * Default constructor for the User class.
     * Initializes a new User instance with default values.
     * The user starts with an empty movie list, zero account balance, and non-premium status.
     */
    public User() {
        this.money = 0;
        this.premium = false;
        this.userMovies = new ArrayList<>();
    }

    /**
     * Sets the password for the user using BCrypt hashing.
     *
     * @param password The plain text password to be hashed and set.
     */
    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray()); 
    }

    /**
     * Tops up the user's account balance with the specified amount.
     *
     * @param amount The amount to be added to the user's account balance.
     */
    public void topUpAccount(int amount) {
        money += amount;
    }
}

