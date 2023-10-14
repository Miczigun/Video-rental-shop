/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.video_rental_shop;

import com.mycompany.video_rental_shop.model.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 *
 * @author Miczi
 */
public class VideoRentalShop {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
        
        // Create an EntityManager
        EntityManager em = emf.createEntityManager();
        
        // Create a new genre
        Genre newGenre = new Genre();
        newGenre.setName("Thriller"); // Set the genre name
        
        // Start a transaction
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        // Persist the new genre
        em.persist(newGenre);
        
        // Commit the transaction to save the changes to the database
        transaction.commit();
        
        // Close the EntityManager and EntityManagerFactory
        em.close();
        emf.close();
    }
}
