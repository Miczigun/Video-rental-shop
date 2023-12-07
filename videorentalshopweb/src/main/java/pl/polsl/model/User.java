/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miczi
 */
@Getter
@Setter
public class User {
    private String username;
    private String password;
    private List<Movie> userMovies;
    private int money;
    private boolean premium = false;
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    
    public void setPassword(String password){
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray()); 
    }
}
