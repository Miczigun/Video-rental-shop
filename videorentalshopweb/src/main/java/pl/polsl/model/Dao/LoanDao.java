/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.model.Loan;
import pl.polsl.util.ConnectionFactory;

/**
 * Data Access Object (DAO) class for handling Loan entities.
 * Manages database operations related to Loan objects.
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class LoanDao {
    
    /**
    * Manages the database connection for LoanDao operations.
    * The connection is initialized in the constructor and closed appropriately.
    */
    private Connection connection;
    
    /**
     * Constructs a LoanDao object and initializes the database connection.
     */
    public LoanDao(){
        this.connection = ConnectionFactory.getConnection();
    }
    
    /**
     * Retrieves a list of Loan objects associated with a given user.
     *
     * @param userId The ID of the user.
     * @return A list of Loan objects associated with the user.
     */
    public List<Loan> getAllUserMovies(int userId) {
        List<Loan> loanList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT loan.id as id, title, brought_date, taken_date, return_status FROM LOAN INNER JOIN MOVIE"
                        + " ON LOAN.MOVIE_ID = MOVIE.ID WHERE MEMBER_ID = ?")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                loanList.add(extractLoanFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return loanList;
    }
    
    /**
     * Updates the return status of a movie associated with a loan.
     *
     * @param loanId The ID of the loan.
     */
    public void returnMovie(int loanId){
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE LOAN SET return_status = true, brought_date = ? WHERE id = ?",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, loanId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    
    /**
     * Checks if a user has an active loan for a specific movie.
     *
     * @param userId The ID of the user.
     * @param movieId The ID of the movie.
     * @return True if the user has an active loan for the movie, false otherwise.
     */
    public boolean checkUserHasMovie(int userId, int movieId){
        
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM LOAN WHERE MEMBER_ID = ? AND MOVIE_ID = ? AND RETURN_STATUS = false")) {
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()){
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return false;
    }
    
    /**
     * Extracts a Loan object from a ResultSet.
     *
     * @param resultSet The ResultSet containing loan-related data.
     * @return A Loan object with data extracted from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private Loan extractLoanFromResultSet(ResultSet resultSet) throws SQLException {        
        Loan loan = new Loan();
        loan.setId(resultSet.getInt("id"));
        loan.setTitle(resultSet.getString("title"));
        loan.setBroughtDate(resultSet.getDate("brought_date"));
        loan.setReturnStatus(resultSet.getBoolean("return_status"));
        loan.setTakenDate(resultSet.getDate("taken_date"));

        return loan;
    }
}
