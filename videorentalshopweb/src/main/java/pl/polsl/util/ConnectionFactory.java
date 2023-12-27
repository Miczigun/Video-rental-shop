package pl.polsl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String JDBC_URL = "jdbc:derby://localhost:1527/video";
    private static final String USERNAME = "app";
    private static final String PASSWORD = "app";

    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            throw new RuntimeException("Failed to establish a database connection.", e);
        }
    }

}
