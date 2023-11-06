/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import pl.polsl.model.Movie;
import pl.polsl.model.User;

/**
 *
 * @author Michal Lajczak
 * @version 1.0
 */
public class UserController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    /**
     * This constructor create connection with database and allows to create queries
     */
    public UserController(){
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    /**
     * This method create user and add user to database
     * @param username
     * @param password 
     */
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
    /**
     * This method login user, if data are correct
     * @param username
     * @param password
     * @return 
     */
    public User loginUser(String username, String password){
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            if (BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
                return user; // Successful login
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This method find user by id
     * @param id
     * @return 
     */
    public User getUserById(long id){
        return entityManager.find(User.class, id);
    }
    
    public User getUserByName(String name){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class);
        query.setParameter("name", name);
        try {
            User user = query.getSingleResult();
            return user;            
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * This method allows to top up the account
     * @param user_id
     * @param money 
     */
    public void topUpTheAccount(long user_id, int money){
        try {
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, user_id);
        double currentBalance = user.getBalance();
        user.setBalance(money + currentBalance);
        entityManager.getTransaction().commit();
        } catch(Exception e){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    
    /**
     * This method allows to buy movie, if user has money on account
     * @param user
     * @param movie 
     */
    public void buyMovie(User user, Movie movie){
        double discount = 1;
        if(user.getPremium()){
            discount = 0.7;
        }
        if (user.getBalance() >= (movie.getPrice() * discount)){
            try {
                entityManager.getTransaction().begin();

                user.setBalance(user.getBalance() - (movie.getPrice() * discount));
                List<Movie> userMovies = user.getMovies();
                userMovies.add(movie); 

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
    
    /**
     * This method allows to buy premium which gives 30% discount on movies
     * @param user
     * @return 
     */
    public boolean buyPremium(User user){
        if(user.getPremium()){
            return false;
        }
        
        if (user.getBalance() >= 60) {
            try {
                entityManager.getTransaction().begin();
                user.setBalance(user.getBalance() - 60);
                user.setPremium(true);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                e.printStackTrace();
                return false;
            }
        }
        else{
            return false;
        }
        
    }
}