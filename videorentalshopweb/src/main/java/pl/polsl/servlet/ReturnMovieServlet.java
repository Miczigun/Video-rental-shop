/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import pl.polsl.model.Dao.LoanDao;

/**
 * Servlet responsible for handling movie return operations.
 * <p>
 * This servlet processes requests related to returning movies, updating the loan status
 * and redirecting users accordingly.
 * </p>
 *
 * @author Michal Lajczak
 * @version 1.5
 */
@WebServlet(name = "ReturnMovieServlet", urlPatterns = {"/return"})
public class ReturnMovieServlet extends HttpServlet {

    /**
     * Data Access Object for handling movie loans and returns.
     */
    private LoanDao loanDao = new LoanDao();

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
        
        // Retrieve user ID from cookies
        Cookie[] cookies = request.getCookies();
        Cookie newCookie = new Cookie("userId", null);
        
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("userId")){
                newCookie = cookie;
            }
        }
        
        // Redirect to login if user ID is not present
        if (newCookie.getValue() == null || newCookie.equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Process movie return based on loan ID
            int loan_id = Integer.parseInt(request.getParameter("id"));
            loanDao.returnMovie(loan_id);
            
            // Redirect to user profile page after movie return
            response.sendRedirect(request.getContextPath() + "/user");        
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
        // No POST handling for this servlet
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet responsible for handling movie return operations.";
    }// </editor-fold>

}
