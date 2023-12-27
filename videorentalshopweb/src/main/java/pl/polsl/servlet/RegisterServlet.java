/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import pl.polsl.model.Genre;
import pl.polsl.model.User;
import pl.polsl.model.UserDao;

/**
 * Servlet implementation for handling user registration in the video rental shop application.
 *
 * This servlet provides functionality for users to register an account, including processing
 * registration requests, validating user input, and interacting with the central logic instance
 * {@link pl.polsl.model.ModelLogic} to create new user accounts.
 *
 * @author Michal Lajczak
 * @version 1.4
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    /**
    * The central logic instance for managing user and movie data in the video rental shop.
    *
    * This variable holds the singleton instance of {@link pl.polsl.model.ModelLogic},
    * which is used throughout the application to access and manipulate user and movie data.
    */
    private UserDao userDao = new UserDao();
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/register.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Horror");
        userDao.addGenre(genre);
        
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String cpassword = request.getParameter("cpassword");
//
//        if (!Objects.equals(password, cpassword)) {
//            // Password and Confirm Password do not match
//            request.setAttribute("error", "Passwords do not match");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/register.jsp");
//            dispatcher.forward(request, response);
//            return;
//        }
//
//        // Additional validation and error handling can be added as needed
//
//
//        // Hash the password using BCrypt or your preferred hashing method
//        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
//
//        // Create a new user
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(hashedPassword);
//
//        // Add the user to the database
//        userDao.addUser(user);
//
//        // Redirect to login page upon successful registration
//        response.sendRedirect(request.getContextPath() + "/login");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
