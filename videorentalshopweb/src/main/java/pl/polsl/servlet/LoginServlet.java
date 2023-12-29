/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import pl.polsl.model.Dao.UserDao;
import pl.polsl.model.User;


/**
 * Servlet implementation for handling user login in the video rental shop application.
 * <p>
 * This servlet provides functionality for users to log in, including processing
 * login requests, validating user credentials, and interacting with the central logic
 * instance {@link pl.polsl.model.Dao.UserDao} to authenticate users.
 * </p>
 * @author Michal Lajczak
 * @version 1.5
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    /**
     * Data Access Object for handling user-related database operations.
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/login.jsp");
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
               
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String message = userDao.loginUser(username, password);
        
        if (message.equals(username)){
            User user = userDao.getUserByUsername(username);
            Cookie userCookie = new Cookie("userId", Long.toString(user.getId()));
            userCookie.setMaxAge(3600);
            response.addCookie(userCookie);
            response.sendRedirect(request.getContextPath() + "/menu");
        } else {
            request.setAttribute("error", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/login.jsp");
            dispatcher.forward(request, response);
        }
        
        
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
