/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 * The central logic class that manages user and movie data for the video rental shop.
 *
 * This class provides methods for loading movies from a JSON file, creating and logging in users,
 * finding users and movies, buying premium memberships, and purchasing movies.
 *
 * @author Michal Lajczak
 * @version 1.4
 */
@Getter
@Setter
public class ModelLogic {

    /** The singleton instance of the ModelLogic class. */
    private static final ModelLogic instance = new ModelLogic();

    /** The list of movies available in the video rental shop. */
    private List<Movie> movies = new ArrayList<>();

    /** The list of registered users in the video rental shop. */
    private List<User> users = new ArrayList<>();

    /**
     * Private constructor to enforce singleton pattern and initialize movie data.
     */
    private ModelLogic() {
        loadMovies();
    }

    /**
     * Retrieves the singleton instance of the ModelLogic class.
     *
     * @return The singleton instance of the ModelLogic class.
     */
    public static ModelLogic getInstance() {
        return instance;
    }

    /**
     * Loads movie data from a JSON file and populates the movies list.
     */
    public void loadMovies() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File("C:\\Users\\micha\\Desktop\\studia\\sem5\\JwIiUM\\Projekt\\Video-rental-shop\\videorentalshopweb\\movies.json");
            movies = objectMapper.readValue(file, new TypeReference<List<Movie>>() {});
        } catch (IOException e) {
            // Handle exception
        }
    }

    /**
     * Creates a new user with the provided username, password, and confirmation password.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @param cpassword The confirmation password for the new user.
     * @return A status message indicating the success or failure of the user creation process.
     */
    public String createUser(String username, String password, String cpassword){
        if (username.isEmpty() || username == null) {
            return "Username field can not be empty!";
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "That username is taken";
            }
        }
        if (password.length() < 8) {
            return "Password must have at least 8 characters";
        }
        if (!cpassword.equals(password)){
            return "Password and Confirm Password do not match!";
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        users.add(newUser);
        
        return "Success";
    }

    /**
     * Logs in a user with the provided username and password.
     *
     * @param username The username of the user trying to log in.
     * @param password The password of the user trying to log in.
     * @return A status message indicating the success or failure of the login process.
     */    
    public String loginUser(String username, String password){
        if (username.isEmpty()){
            return "Enter username!";
        }
        if (password.isEmpty()){
            return "Enter password!";
        }
        for (User user : users) {
            if (user.getUsername().equals(username)){               
                if (BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified){
                    return username;
                } else {
                    return "Invalid password!";
                }                
            }
        }
        return "User does not exist!";
    }
    
    /**
     * Finds and retrieves a user by their username.
     *
     * @param username The username of the user to find.
     * @return The found User object or null if no user with the given username is found.
     */    
    public User findUserByName(String username) {
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        return userOptional.orElse(null);
    }
    
     /**
     * Finds and retrieves a movie by its title.
     *
     * @param title The title of the movie to find.
     * @return The found Movie object or null if no movie with the given title is found.
     */   
    public Movie findMovieByTitle(String title){
        Optional<Movie> movieOptional = movies.stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst();
        return movieOptional.orElse(null);
    }
    
     /**
     * Buys a premium membership for the specified user.
     *
     * @param user The user to buy the premium membership for.
     * @return A status message indicating the success or failure of the premium purchase process.
     */   
    public String buyPremium(User user){
        if (user.isPremium()){
            return "You are premium user!";
        }
        if (user.getMoney() < 50){
            return "You do not have enough money to buy premium";
        }
        int userMoney = user.getMoney();
        userMoney -= 50;
        user.setMoney(userMoney);
        user.setPremium(true);
        return "You are premium user now!";
    }
    
    /**
     * Buys a movie for the specified user.
     *
     * @param user The user buying the movie.
     * @param movie The movie to be bought.
     * @return A status message indicating the success or failure of the movie purchase process.
     */    
    public String buyMovie(User user, Movie movie){
        if (user == null){
            return "User does not exist!";
        }
        if (movie == null){
            return "Movie does not exist!";
        }
        for (Movie uMovie : user.getUserMovies()){
            if (uMovie.getTitle().equals(movie.getTitle())){
                return "You have that movie!";
            }
        }
        
        double discount = user.isPremium() ? 0.7 : 1;
        if (user.getMoney() < (movie.getPrice() * discount)){
            return "You do not have enough money to buy that movie!";
        }
        
        int userMoney = user.getMoney() - (int)(movie.getPrice() * discount);
        user.getUserMovies().add(movie);
        user.setMoney(userMoney);
        
        return "Success";
    }
    
}
