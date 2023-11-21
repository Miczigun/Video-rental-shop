/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import pl.polsl.model.MovieDao;
import pl.polsl.model.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import pl.polsl.model.User;

/**
 *
 * @author Miczi
 */
public class AdminDao {
    private UserDao userController;
    private MovieDao movieController;

    public AdminDao() {
        this.movieController = new MovieDao();
        this.userController = new UserDao();
    }
    
    public boolean generateReport(){
        try {            
        List<User> users = userController.getAllUsers();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        //String jsonString = objectMapper.writeValueAsString(users);
        
        File file = new File("report.json");
        objectMapper.writeValue(file, users);
        return true;
        } catch (IOException e) {
            return false;
        }
    }
    
}
