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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Michal Lajczak
 * @version 1.0
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String username;
    private String password;  
    private boolean premium = false;
    private double balance;
    
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private List<Movie> movies;
    
    /**
     * Set password using Bcrypt function
     * @param password 
     */
    public void setPassword(String password){
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());    
    }
    /**
     * This method return 'premium' variable and if return value is true, user has premium
     * @return 
     */
    public boolean getPremium(){
        return this.premium;
    }
}
