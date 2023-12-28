package pl.polsl.model.Dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pl.polsl.model.Loan;
import pl.polsl.model.Movie;
import pl.polsl.model.User;
import pl.polsl.util.ConnectionFactory;

public class UserDao {

    private Connection connection;
    private MovieDao movieDao;
    private LoanDao loanDao;
    
    public UserDao() {
        this.connection = ConnectionFactory.getConnection();
        this.movieDao = new MovieDao();
        this.loanDao = new LoanDao();
    }
    
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
    
    public String registerUser(String username, String password, String cPassword){
        User user = getUserByUsername(username);
        if (user != null){
            return "That username is taken";
        }
        if (username.isEmpty()){
            return "Username field can not be empty!";
        }
        if (password.length() < 8){
            return "Password must have minimum 8 characters";
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
    
    public String buyPremium(int userId){
        User user = getUserById(userId);
        if (user == null){
            return "User does not exist";
        }
        
        if (user.getPremium()){
            return "You are premium user!";
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
            return "You are premium user now!";
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return "Failed";
    }
    
    public String buyMovie(int userId, int movieId){
        User user = getUserById(userId);
        Movie movie = movieDao.getMovieById(movieId);
        Loan loan = loanDao.checkUserHasMovie(userId, movieId);
        if (user == null){
            return "User does not exist!";
        }
        if (movie == null){
            return "That movie does not exist";
        }
        if (loan != null){
            return "You have that movie!";
        }
        
        double discount = user.getPremium() ? 0.7 : 1;
        if (user.getBalance() < movie.getPrice() * discount){
            return "You do not have enough money";
        }
        
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO LOAN (member_id, movie_id, return_status, taken_date VALUES (?,?,?,?))",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, movieId);
            statement.setBoolean(3, false);
            statement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            
            statement.executeUpdate();
            
            topUpAccount(userId, (int) -(movie.getPrice() * discount));
            return "Succes";
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return "Failed";
    }
    
    public User getUserById(long userId) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBER WHERE id = ?")) {
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return null;
    }
    
    public User getUserByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBER WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MEMBER")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userList.add(extractUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return userList;
    }

    public void topUpAccount(int userId, int amount){
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE MEMBER SET money = money + ? WHERE id = ?",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, amount);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    
    public List<Loan> userMovies(int userId){
        return loanDao.getAllUserMovies(userId);
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPasswordWithoutHash(resultSet.getString("password"));
        user.setPremium(resultSet.getBoolean("premium"));
        user.setBalance(resultSet.getInt("money"));
        user.setAdmin(resultSet.getBoolean("admin_status"));
        // Populate other fields as needed

        return user;
    }
    
   
}
