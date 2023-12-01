/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.videorentalshop;

import pl.polsl.model.UserDao;
import pl.polsl.gui.MainFrame;

/**
 * Entry point for the Video Rental Shop application.
 * 
 * Initializes the main frame and sets up the necessary controllers.
 * 
 * Note: This version assumes no command-line arguments are used.
 * 
 * @author Miczi
 */
public class VideoRentalShop {

    /**
     * Main method to start the Video Rental Shop application.
     * 
     * @param args The command-line arguments (not used in this version).
     */
    public static void main(String[] args) {
        // Create the main frame of the application
        MainFrame mainFrame = new MainFrame();
        
        // Create a controller for managing user-related operations
        UserDao userController = new UserDao();
        
        // Pass the UserDao instance to the MainFrame
        mainFrame.setUserController(userController);
        
        // Set the main frame to be visible
        mainFrame.setVisible(true);
    }
}

