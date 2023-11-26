/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Data Access Object (DAO) for managing User entities.
 *
 * @author Michal Lajczak
 * @version 1.2
 */
public class UserDao {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    /**
     * This constructor create connection with database and allows to create queries
     */
    public UserDao(){
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    /**
     * Creates a new user and adds them to the database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user (hashed automatically).
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
     * Logs in a user if the provided credentials are correct.
     *
     * @param username The username of the user trying to log in.
     * @param password The password of the user trying to log in.
     * @return The User object if login is successful, otherwise null.
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
     * Finds a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The User object if found, otherwise null.
     */
    public User getUserById(long id){
        return entityManager.find(User.class, id);
    }
    
    /**
     * Retrieves a list of all users in the database.
     *
     * @return List of User objects representing all users in the database.
     */    
    public List<User> getAllUsers(){
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    /**
     * Finds a user by their username.
     *
     * @param name The username of the user to be retrieved.
     * @return The User object if found, otherwise null.
     */    
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
     * Tops up the account balance for a user.
     *
     * @param user_id The ID of the user whose account is to be topped up.
     * @param money The amount to be added to the user's balance.
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
     * Allows a user to buy a movie if they have sufficient funds.
     *
     * @param user The user attempting to buy the movie.
     * @param movieId The ID of the movie to be purchased.
     * @return True if the purchase is successful, otherwise false.
     */
    public boolean buyMovie(User user, long movieId){
       
        double discount = user.getPremium() ? 0.7 : 1.0;
                
        try {
            entityManager.getTransaction().begin();
            
            Movie movie = entityManager.find(Movie.class, movieId);
            if (user.getBalance() < (movie.getPrice() * discount)) {
                return false;
            }
            user.setBalance(user.getBalance() - (movie.getPrice() * discount));
            user.getMovies().add(movie);
            entityManager.merge(user);

            entityManager.getTransaction().commit();
            System.out.println(user.getMovies());
            entityManager.refresh(user);
            System.out.println(user.getMovies());
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }        
    }
    
    /**
     * Allows a user to buy premium, providing a 30% discount on movies.
     *
     * @param user The user attempting to buy premium.
     * @return True if the purchase is successful, otherwise false.
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
    
    /**
     * Checks if a user has a specific movie in their collection.
     *
     * @param user The user to check.
     * @param movieId The ID of the movie to check for.
     * @return True if the user has the movie, otherwise false.
     */    
    public boolean findUserMovie(User user, long movieId){
        try {
            for (Movie movie : user.getMovies()) {
                if (movie.getId() == movieId){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Removes a movie from a user's collection.
     *
     * @param user The user from whose collection the movie is to be removed.
     * @param movieId The ID of the movie to be removed.
     * @return True if the removal is successful, otherwise false.
     */    
    public boolean removeUserMovie(User user, long movieId){
        try {
            entityManager.getTransaction().begin();
            
            Movie movie = entityManager.find(Movie.class, movieId);
            if (movie == null) {
                return false;
            }
            user.getMovies().remove(movie);
            entityManager.merge(user);
            
            entityManager.getTransaction().commit();;
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
            return false;
        }
    }
    
    /**
     * Deletes a user from the database by their ID.
     *
     * @param userId The ID of the user to be deleted.
     */    
    public void deleteUser(long userId){
        try {
            entityManager.getTransaction().begin();
            
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
            }
            
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        e.printStackTrace();
        }
        
    }
}