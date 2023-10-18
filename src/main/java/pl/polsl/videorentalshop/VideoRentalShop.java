/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.videorentalshop;

import pl.polsl.model.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import pl.polsl.model.Movie;
import pl.polsl.controller.MovieController;
import pl.polsl.controller.GenreController;

/**
 *
 * @author Miczi
 */
public class VideoRentalShop {

    public static void main(String[] args) {
        GenreController gc = new GenreController();
        MovieController mc = new MovieController();
        Movie newMovie = new Movie();
        Genre drama = new Genre();
        
        drama = gc.getGenre(1);
        newMovie.setTitle("Titanic");
        newMovie.setPrice(20);
        newMovie.setYear(1997);
        newMovie.setRating(7.9f);
        newMovie.setGenre(drama);
        
        mc.addMovie(newMovie);
        List<Movie> movies = mc.getAllMovies();
        for (Movie movy : movies) {
            System.out.println(movy);
        }
    }
}
