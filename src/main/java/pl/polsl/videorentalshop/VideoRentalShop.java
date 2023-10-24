/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.videorentalshop;

import pl.polsl.controller.UserController;
import pl.polsl.gui.MainFrame;

/**
 *
 * @author Miczi
 */
public class VideoRentalShop {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        UserController userController = new UserController();
        mainFrame.setUserController(userController); // Pass the UserController to the MainFrame
        mainFrame.setVisible(true);
    }
//        GenreController gc = new GenreController();
//        MovieController mc = new MovieController();
//        UserController uc = new UserController();
//        User newUser = new User();
//        Movie newMovie = new Movie();
//        Genre drama = new Genre();
        
//        drama = gc.getGenre(5);
//        newMovie.setTitle("Pulp Fiction");
//        newMovie.setPrice(15);
//        newMovie.setYear(1994);
//        newMovie.setRating(8.9f);
//        newMovie.setGenre(drama);
        
        //mc.addMovie(newMovie);
//        List<Movie> movies = mc.getAllMovies();
//        for (Movie movy : movies) {
//            System.out.println(movy);
//        }
//        
//        //uc.createUser("miczi", "miczi");
//        newUser = uc.loginUser("miczi", "miczi");
//        System.out.println(newUser);
//        //uc.topUpTheAccount(newUser.getId(), 100);
//        //uc.buyPremium(newUser);
//        //uc.buyMovie(newUser, mc.getMovieById(452));
//        for (Movie movy : newUser.getMovies()) {
//            System.out.println("Tytuł");
//            System.out.println(movy);
//        }
//    }
}
