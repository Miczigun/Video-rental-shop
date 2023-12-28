/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.model.Loan;
import pl.polsl.util.ConnectionFactory;

/**
 *
 * @author Miczi
 */
public class LoanDao {
    
    private Connection connection;
    
    public LoanDao(){
        this.connection = ConnectionFactory.getConnection();
    }
    
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
    
    public void returnMovie(int loanId){
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE LOAN SET return_status = true WHERE id = ?",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, loanId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    
    public Loan checkUserHasMovie(int userId, int movieId){
        
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM LOAN WHERE MEMBER_ID = ? AND MOVIE_ID = ? AND RETURN_STATUS = FALSE")) {
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()){
                return extractLoanFromResultSet(resultSet);
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return null;
    }
    
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
