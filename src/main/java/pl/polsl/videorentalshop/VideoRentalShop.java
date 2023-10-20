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
import pl.polsl.controller.UserController;
import pl.polsl.model.User;

/**
 *
 * @author Miczi
 */
public class VideoRentalShop {

    public static void main(String[] args) {
        GenreController gc = new GenreController();
        MovieController mc = new MovieController();
        UserController uc = new UserController();
        User newUser = new User();
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
        
        uc.createUser("miczi", "miczi");
        newUser = uc.getUserById(1);
//        uc.topUpTheAccount(newUser.getId(), 100);
//        uc.buyMovie(newUser, mc.getMovieById(52));
        for (Movie movy : newUser.getMovies()) {
            System.out.println("Tytuł");
            System.out.println(movy);
        }
    }
}
