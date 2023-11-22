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
 *
 * @author Michal Lajczak
 * @version 1.0
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
    
    public List<User> getAllUsers(){
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
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
}