/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import pl.polsl.model.Movie;

/**
 *
 * @author Miczi
 */
public class MovieController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public MovieController(){
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
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
    
    public Movie getMovieById(long id) {
        return entityManager.find(Movie.class,id);
    }
    
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
    
    public List<Movie> getAllMovies() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }
    
    public List<Movie> getMoviesByGenre(int genre_id){
        return entityManager.createQuery("SELECT m FROM Movie m WHERE m.genre = :genre_id ", Movie.class).setParameter(genre_id, this).getResultList();
    }
}
