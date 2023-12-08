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
 *
 * @author Miczi
 */
@Getter
@Setter
public class ModelLogic {
    
    private static final ModelLogic instance = new ModelLogic();
    
    private List<Movie> movies = new ArrayList<Movie>();
    private List<User> users = new ArrayList<User>();
    
    private ModelLogic(){
        loadMovies();
    }
    
    public static ModelLogic getInstance(){
        return instance;
    }
    
    public void loadMovies(){
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            
            File file = new File("C:\\Users\\micha\\Desktop\\studia\\sem5\\JwIiUM\\Projekt\\Video-rental-shop\\videorentalshopweb\\movies.json");
            movies = objectMapper.readValue(file, new TypeReference<List<Movie>>() {});
            
        } catch (IOException e) {
            
        }
    }
    
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
            return "Password must have atleast 8 characters";
        }
        if (!cpassword.equals(password)){
            return "Password and Confirm Password does not match!";
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        users.add(newUser);
        
        return "Success";
    }
    
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
    
    public User findUserByName(String username) {
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        return userOptional.orElse(null);
    }
}
