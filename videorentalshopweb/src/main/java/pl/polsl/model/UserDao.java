package pl.polsl.model;

import pl.polsl.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pl.polsl.util.ConnectionFactory;

public class UserDao {

    private Connection connection;

    public UserDao() {
        this.connection = ConnectionFactory.getConnection();
    }
    
    public void addGenre(Genre genre){
            try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO GENRE (id, name) VALUES (?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, genre.getId());
            statement.setString(2, genre.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    public void addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user (username, password, premium, balance, admin) VALUES (?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getPremium());
            statement.setDouble(4, user.getBalance());
            statement.setBoolean(5, user.isAdmin());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public User getUserById(long userId) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id = ?")) {
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

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userList.add(extractUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return userList;
    }

    // Add other methods as needed

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setPremium(resultSet.getBoolean("premium"));
        user.setBalance(resultSet.getDouble("balance"));
        user.setAdmin(resultSet.getBoolean("admin"));
        // Populate other fields as needed

        return user;
    }
}
