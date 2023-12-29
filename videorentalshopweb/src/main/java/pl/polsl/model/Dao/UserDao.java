/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model.Dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.model.Loan;
import pl.polsl.model.Movie;
import pl.polsl.model.User;
import pl.polsl.util.ConnectionFactory;

/**
 * Data Access Object (DAO) class for handling User entities.
 * Manages database operations related to User objects.
 * Provides methods for user registration, login, account management, and movie transactions.
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class UserDao {

    /**
     * Manages the database connection for UserDao operations.
     * The connection is initialized in the constructor and closed appropriately.
     */
    private Connection connection;
    
    /**
     * Data Access Object for Movie entities.
     * Manages database operations related to Movie objects.
     */
    private MovieDao movieDao;
    
    /**
     * Data Access Object for Loan entities.
     * Manages database operations related to Loan objects.
     */
    private LoanDao loanDao;

    /**
     * Constructs a UserDao object and initializes the database connection,
     * MovieDao, and LoanDao objects.
     */
    public UserDao() {
        this.connection = ConnectionFactory.getConnection();
        this.movieDao = new MovieDao();
        this.loanDao = new LoanDao();
    }

    /**
     * Adds a new user to the database.
     *
     * @param user The User object to be added.
     * @return True if the user was added successfully, false otherwise.
     */
    public boolean addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO MEMBER (username, password, premium, money, admin_status) VALUES (?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getPremium());
            statement.setInt(4, user.getBalance());
            statement.setBoolean(5, user.isAdmin());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
        return false;
    }

    /**
     * Registers a new user with the provided username and password.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param cPassword The confirmation password entered by the user.
     * @return A status message indicating the result of the registration attempt.
     */
    public String registerUser(String username, String password, String cPassword){
        User user = getUserByUsername(username);
        if (user != null){
            return "That username is taken";
        }
        if (username.isEmpty()){
            return "Username field can not be empty!";
        }
        if (password.length() < 8){
            return "Password must have a minimum of 8 characters";
        }
        if (!password.equals(cPassword)){
            return "Password and Confirm password have to be the same!";
        }
        
        user = new User(username, password);
        if (addUser(user)){
            return "Success";
        }
        
        return "Failed";
    }

    /**
     * Logs in a user with the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return A status message indicating the result of the login attempt.
     */
    public String loginUser(String username, String password){
        if (username.isEmpty() || password.isEmpty()){
            return "Please enter your login details";
        }
        
        User user = getUserByUsername(username);
        if (user == null){
            return "User with this name does not exist";
        }
        
        if (BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
            return username;
        } else {
            return "Incorrect password";
            
        }
    }

    /**
     * Enables a user to purchase a premium membership.
     *
     * @param userId The ID of the user purchasing premium.
     * @return A status message indicating the result of the premium purchase attempt.
     */
    public String buyPremium(int userId){
        User user = getUserById(userId);
        if (user == null){
            return "User does not exist";
        }
        
        if (user.getPremium()){
            return "You are a premium user!";
        }
        
        if (user.getBalance() < 50){
            return "You do not have enough money to buy premium";
        }
        
        int userMoney = user.getBalance();
        userMoney -= 50;
        
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE MEMBER SET premium = true, money = ? WHERE id = ?",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userMoney);
            statement.setInt(2, userId);

            statement.executeUpdate();
            return "You are a premium user now!";
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return "Failed";
    }

    /**
     * Enables a user to purchase a movie.
     *
     * @param userId The ID of the user purchasing the movie.
     * @param movieId The ID of the movie being purchased.
     * @return A status message indicating the result of the movie purchase attempt.
     */
    public String buyMovie(int userId, int movieId){
        User user = getUserById(userId);
        Movie movie = movieDao.getMovieById(movieId);
        if (user == null){
            return "User does not exist!";
        }
        if (movie == null){
            return "That movie does not exist";
        }
        if (loanDao.checkUserHasMovie(userId, movieId)){
            return "You already have that movie!";
        }
        
        double discount = user.getPremium() ? 0.7 : 1;
        if (user.getBalance() < movie.getPrice() * discount){
            return "You do not have enough money";
        }
        
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO LOAN (member_id, movie_id, return_status, taken_date) VALUES (?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            statement.setBoolean(3, false);
            statement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            
            statement.executeUpdate();
            
            topUpAccount(userId, (int) -(movie.getPrice() * discount));
            return "Success";
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return "Failed";
    }

    /**
     * Retrieves a User object by its ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return A User object with the specified ID, or null if not found.
     */
    public User getUserById(long userId) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBER WHERE id = ?")) {
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return null;
    }

    /**
     * Retrieves a User object by its username.
     *
     * @param username The username of the user to retrieve.
     * @return A User object with the specified username, or null if not found.
     */
    public User getUserByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBER WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return null;
    }

    /**
     * Retrieves a list of all users from the database.
     *
     * @return A list of User objects representing all users in the database.
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBER")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userList.add(extractUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return userList;
    }

    /**
     * Tops up the account balance of a user by a specified amount.
     *
     * @param userId The ID of the user whose account is being topped up.
     * @param amount The amount to be added to the user's account balance.
     */
    public void topUpAccount(int userId, int amount){
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE MEMBER SET money = money + ? WHERE id = ?",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, amount);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Retrieves a list of loans associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of Loan objects representing the user's movie loans.
     */
    public List<Loan> userMovies(int userId){
        return loanDao.getAllUserMovies(userId);
    }

    /**
     * Extracts a User object from a ResultSet.
     *
     * @param resultSet The ResultSet containing user-related data.
     * @return A User object with data extracted from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPasswordWithoutHash(resultSet.getString("password"));
        user.setPremium(resultSet.getBoolean("premium"));
        user.setBalance(resultSet.getInt("money"));
        user.setAdmin(resultSet.getBoolean("admin_status"));

        return user;
    }
   
}
