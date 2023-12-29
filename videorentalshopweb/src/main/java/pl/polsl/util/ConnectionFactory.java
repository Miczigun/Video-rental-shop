package pl.polsl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a factory for creating database connections.
 * <p>
 * This utility class encapsulates the logic for establishing a connection to the database.
 * It uses JDBC to connect to a Derby database with specified URL, username, and password.
 * </p>
 * <p>
 * The main functionality is provided through the static method {@link #getConnection()}.
 * This method returns a {@link Connection} object that can be used to interact with the database.
 * </p>
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class ConnectionFactory {

    /**
     * The JDBC URL for connecting to the Derby database.
     */
    private static final String JDBC_URL = "jdbc:derby://localhost:1527/video";

    /**
     * The username for authenticating the database connection.
     */
    private static final String USERNAME = "app";

    /**
     * The password for authenticating the database connection.
     */
    private static final String PASSWORD = "app";

    /**
     * Retrieves a connection to the database.
     * <p>
     * This method uses the JDBC driver for Derby and establishes a connection to the
     * specified database with the provided username and password.
     * </p>
     *
     * @return A {@link Connection} object representing the database connection.
     * @throws RuntimeException If there is an error establishing the database connection.
     */
    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }
}
