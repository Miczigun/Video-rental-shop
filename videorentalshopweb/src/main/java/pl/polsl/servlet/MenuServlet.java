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
import pl.polsl.model.Dao.MovieDao;
import pl.polsl.model.Dao.UserDao;
import pl.polsl.model.User;

/**
 * Servlet implementation for handling user interactions in the video rental shop application.
 *
 * This servlet handles user requests related to the main menu, such as displaying available movies,
 * handling premium membership purchases, and forwarding users to the appropriate views.
 *
 * @author Michal Lajczak
 * @version 1.4
 */
@WebServlet(name = "MenuServlet", urlPatterns = {"/menu"})
public class MenuServlet extends HttpServlet {
    
    private UserDao userDao = new UserDao();
    private MovieDao movieDao = new MovieDao();
    /**
     * The central logic instance for managing user and movie data in the video rental shop.
     *
     * This variable holds the singleton instance of {@link pl.polsl.model.ModelLogic},
     * which is used throughout the application to access and manipulate user and movie data.
     */   

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
        Cookie newCookie = new Cookie("userId", null);
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("userId")){
                newCookie = cookie;
            }
        }
        if (newCookie.getValue() == null || newCookie.getValue().equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            String status = request.getParameter("status");
            if (status != null){
                request.setAttribute("status", status);
            }
            
//            User user = userDao.getUserById(Long.parseLong(newCookie.getValue()));
//            request.setAttribute("user", user);

            request.setAttribute("movies", movieDao.getAllMovies());
        
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/menu.jsp");       
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
        Cookie newCookie = new Cookie("userId", null);
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("userId")){
                newCookie = cookie;
            }
        }
        if (newCookie.getValue() == null || newCookie.equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            
            //User user = userDao.getUserById(Long.parseLong(newCookie.getValue()));
            String status = userDao.buyPremium(Integer.parseInt(newCookie.getValue()));
            
            request.setAttribute("movies", movieDao.getAllMovies());
            request.setAttribute("status", status);
       
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/menu.jsp");       
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
