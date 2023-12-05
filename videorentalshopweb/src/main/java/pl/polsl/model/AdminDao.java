/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Represents a data access object for administrative operations.
 * It provides functionality to generate a report in JSON format.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
public class AdminDao {

    /**
     * The controller for managing user-related operations.
     */
    private UserDao userController;

    /**
     * The controller for managing movie-related operations.
     */
    private MovieDao movieController;

    /**
     * Constructs an instance of AdminDao with default controllers.
     */
    public AdminDao() {
        this.movieController = new MovieDao();
        this.userController = new UserDao();
    }

    /**
     * Generates a report in JSON format containing information about all users.
     * 
     * @return True if the report is successfully generated, false otherwise.
     */
    public boolean generateReport() {
        try {            
            // Retrieve the list of all users
            List<User> users = userController.getAllUsers();
        
            // Create an ObjectMapper with indentation for better readability
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
            // Uncomment the following line if you want to generate a JSON string instead of a file
            // String jsonString = objectMapper.writeValueAsString(users);
        
            // Specify the file to store the generated report
            File file = new File("report.json");
            
            // Write the user information to the specified file in JSON format
            objectMapper.writeValue(file, users);
            
            return true;
        } catch (IOException e) {
            // Return false if an IOException occurs during the process
            return false;
        }
    }
}

