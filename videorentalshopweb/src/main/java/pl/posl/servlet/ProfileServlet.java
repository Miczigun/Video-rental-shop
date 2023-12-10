/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.posl.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import pl.polsl.model.ModelLogic;
import pl.polsl.model.User;

/**
 * Servlet implementation for handling user profile-related actions in the video rental shop application.
 *
 * This servlet handles user requests related to viewing and managing their profile,
 * such as displaying user information and movies owned, and topping up the account balance.
 *
 * @author Michal Lajczak
 * @version 1.4
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/user"})
public class ProfileServlet extends HttpServlet {
    
    /**
    * The central logic instance for managing user and movie data in the video rental shop.
    *
    * This variable holds the singleton instance of {@link pl.polsl.model.ModelLogic},
    * which is used throughout the application to access and manipulate user and movie data.
    */
    private ModelLogic modelLogic = ModelLogic.getInstance();

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
        
        Cookie[] cookies = request.getCookies();
        Cookie newCookie = new Cookie("user", null);
        
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("user")){
                newCookie = cookie;
            }
        }
        
        if (newCookie.getValue() == null || newCookie.equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            User user = modelLogic.findUserByName(newCookie.getValue());
            request.setAttribute("user", user);
            request.setAttribute("movies", user.getUserMovies());
        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user.jsp");       
            dispatcher.forward(request, response);
        }

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
        
        Cookie[] cookies = request.getCookies();
        Cookie newCookie = new Cookie("user", null);
        
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("user")){
                newCookie = cookie;
            }
        }
        
        if (newCookie.getValue() == null || newCookie.equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            int amount = Integer.parseInt(request.getParameter("amount"));
            User user = modelLogic.findUserByName(newCookie.getValue());
            user.topUpAccount(amount);
            request.setAttribute("user", user);
            request.setAttribute("movies", user.getUserMovies());
        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user.jsp");       
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
