/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import pl.polsl.model.Genre;

/**
 *
 * @author Miczi
 */
public class GenreController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public GenreController(){
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    public Genre getGenre(int id){
        return entityManager.find(Genre.class, id);
    }
    
    public List<Genre> getGenres(){
        return entityManager.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
    }
}
