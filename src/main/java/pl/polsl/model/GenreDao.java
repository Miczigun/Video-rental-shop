/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) for managing Genre entities and performing database operations.
 * It provides methods to retrieve Genre objects by ID and retrieve a list of genre names.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
public class GenreDao {

    /**
     * The EntityManagerFactory used to create an EntityManager.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * The EntityManager used to perform database operations.
     */
    private EntityManager entityManager;

    /**
     * Constructs a GenreDao and establishes a connection with the database.
     */
    public GenreDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Retrieves a Genre object from the database based on the given ID.
     * 
     * @param id The unique identifier of the genre.
     * @return The Genre object if found, otherwise null.
     */
    public Genre getGenre(int id) {
        return entityManager.find(Genre.class, id);
    }

    /**
     * Retrieves a list of genre names from the database.
     * 
     * @return A list of genre names.
     */
    public List<String> getGenres() {
        // Execute a query to retrieve all Genre entities from the database
        List<Genre> genres = entityManager.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();

        // Map the list of Genre entities to a list of genre names using Java streams
        List<String> genresNames = genres.stream().map(Genre::getName).collect(Collectors.toList());

        return genresNames;
    }
}

