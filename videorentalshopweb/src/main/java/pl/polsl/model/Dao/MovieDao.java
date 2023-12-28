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
import pl.polsl.model.User;
import pl.polsl.util.ConnectionFactory;

/**
 *
 * @author Miczi
 */
public class MovieDao {
    
    private Connection connection;
    
    public MovieDao(){
        this.connection = ConnectionFactory.getConnection();
    }
    
    public Movie getMovieById(long movieId) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MOVIE INNER JOIN GENRE ON MOVIE.GENRE_ID = GENRE.ID WHERE id = ?")) {
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
    
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM MOVIE INNER JOIN GENRE ON MOVIE.GENRE_ID = GENRE.ID")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                movieList.add(extractMovieFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return movieList;
    }

    // Add other methods as needed

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
