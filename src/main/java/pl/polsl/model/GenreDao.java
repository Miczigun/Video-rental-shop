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
import pl.polsl.model.Genre;

/**
 *
 * @author Michal Lajczak
 * @version 1.0
 */
public class GenreDao {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    /**
     * This constructor create connection with database and allows to create queries
     */
    public GenreDao(){
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    /**
     * This method find genre by id and return Genre object
     * @param id
     * @return 
     */
    public Genre getGenre(int id){
        return entityManager.find(Genre.class, id);
    }
    
    /**
     * This method return list of genres names
     * @return 
     */
    public List<String> getGenres(){
        List<Genre> genres =  entityManager.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        
        List<String> genresNames = genres.stream().map(Genre::getName).collect(Collectors.toList());
        
        return genresNames;
                                            
    }
}
