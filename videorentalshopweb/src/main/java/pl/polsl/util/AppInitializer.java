/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class AppInitializer implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.createGenre();
        databaseInitializer.createMovie();
        databaseInitializer.createMember();
        databaseInitializer.createLoan();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method will be called when the application is about to be shut down
    }
}
