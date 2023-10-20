/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pl.polsl.model.Movie;
import pl.polsl.model.User;

/**
 *
 * @author Miczi
 */
public class UserController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public UserController(){
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    public void createUser(String username, String password) {
        try {
            entityManager.getTransaction().begin();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // The password is hashed automatically in the setter
            user.setBalance(0);
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            
        } catch (Exception e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }   
    }
    
    public User getUserById(long id){
        return entityManager.find(User.class, id);
    }
    
    public void topUpTheAccount(long user_id, int money){
        try {
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, user_id);
        int currentBalance = user.getBalance();
        user.setBalance(money + currentBalance);
        entityManager.getTransaction().commit();
        } catch(Exception e){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void buyMovie(User user, Movie movie){
        if (user.getBalance() >= movie.getPrice()){
            try {
                entityManager.getTransaction().begin();

                user.setBalance(user.getBalance() - movie.getPrice());
                user.getMovies().add(movie);

                entityManager.merge(user);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                }
                e.printStackTrace();
            }
        }
    }
}