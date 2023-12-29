/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Initializes the application by creating database tables on application startup.
 *
 * This class implements the ServletContextListener interface to perform
 * initialization tasks when the application starts.
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class AppInitializer implements ServletContextListener {

    /**
     * Called when the application is starting up.
     *
     * This method creates database tables using the DatabaseInitializer class.
     *
     * @param sce The ServletContextEvent containing the ServletContext that is
     * being initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.createGenre();
        databaseInitializer.createMovie();
        databaseInitializer.createMember();
        databaseInitializer.createLoan();
    }

    /**
     * Called when the application is about to be shut down.
     *
     * This method will be called when the application is about to be shut down.
     *
     * @param sce The ServletContextEvent containing the ServletContext that is
     * being destroyed.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method will be called when the application is about to be shut down
    }
}
