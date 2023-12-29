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
import pl.polsl.model.Movie;
import pl.polsl.util.ConnectionFactory;

/**
 * Data Access Object (DAO) class for handling Movie entities.
 * Manages database operations related to Movie objects.
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class MovieDao {
    
    /**
     * Manages the database connection for MovieDao operations.
     * The connection is initialized in the constructor and closed appropriately.
     */
    private Connection connection;
    
    /**
     * Constructs a MovieDao object and initializes the database connection.
     */
    public MovieDao(){
        this.connection = ConnectionFactory.getConnection();
    }
    
    /**
     * Retrieves a Movie object by its ID.
     *
     * @param movieId The ID of the movie.
     * @return A Movie object with the specified ID, or null if not found.
     */
    public Movie getMovieById(long movieId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM MOVIE INNER JOIN GENRE ON MOVIE.GENRE_ID = GENRE.ID WHERE MOVIE.ID = ?")) {
            statement.setLong(1, movieId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractMovieFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return null;
    }
    
    /**
     * Retrieves a list of all movies from the database.
     *
     * @return A list of Movie objects representing all movies in the database.
     */
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM MOVIE INNER JOIN GENRE ON MOVIE.GENRE_ID = GENRE.ID")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                movieList.add(extractMovieFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return movieList;
    }

    /**
     * Extracts a Movie object from a ResultSet.
     *
     * @param resultSet The ResultSet containing movie-related data.
     * @return A Movie object with data extracted from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    private Movie extractMovieFromResultSet(ResultSet resultSet) throws SQLException {
        
        Movie movie = new Movie();
        movie.setId(resultSet.getLong("id"));
        movie.setTitle(resultSet.getString("title"));
        movie.setYear(resultSet.getInt("movie_year"));
        movie.setPrice(resultSet.getInt("price"));
        movie.setGenre(resultSet.getString("name"));

        return movie;
    }
}
