/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import pl.polsl.model.Movie;

/** 
 *
 * @author Michal Lajczak
 * @version 1.2
 */
public class MovieDao {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    /**
     * This constructor create connection with database and allows to create queries
     */
    public MovieDao(){
        entityManagerFactory = Persistence.createEntityManagerFactory("my_persistence_unit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    /**
     * Adds a movie to the database.
     *
     * @param movie The movie to be added.
     */
    public void addMovie(Movie movie){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(movie);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a movie by its ID.
     *
     * @param id The ID of the movie to be retrieved.
     * @return The Movie object if found, otherwise null.
     */
    public Movie getMovieById(long id) {
        return entityManager.find(Movie.class,id);
    }
    
    /**
     * Deletes a movie from the database, if it exists.
     *
     * @param id The ID of the movie to be deleted.
     */
    public void deleteMovie(long id) {
        try {
            entityManager.getTransaction().begin();
            Movie movie = entityManager.find(Movie.class, id);
            if (movie != null) {
                entityManager.remove(movie);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the price of a movie (allowed only for admin).
     *
     * @param id The ID of the movie to be updated.
     * @param price The new price for the movie.
     */
    public void updateMoviePrice(long id, int price){
        try {
            entityManager.getTransaction().begin();
            Movie movie = entityManager.find(Movie.class, id);
            if (movie != null) {
                movie.setPrice(price);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves a list of all movies in the database.
     *
     * @return List of Movie objects representing all movies in the database.
     */
    public List<Movie> getAllMovies() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }
    
    /**
     * Retrieves a list of movies from a specific genre.
     *
     * @param genreName The name of the genre.
     * @return List of Movie objects from the specified genre.
     */
    public List<Movie> getMoviesByGenre(String genreName){
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.genre.name = :genreName ", Movie.class).setParameter("genreName", genreName).getResultList();
    }
}
