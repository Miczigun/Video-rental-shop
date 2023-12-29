/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Michal Lajczak
 * @version 1.5
 */
public class DatabaseInitializer {
    
    private Connection connection;
    
    public DatabaseInitializer(){
        this.connection = ConnectionFactory.getConnection();
    }
    
    public void createGenre(){
        try (Statement stmt = connection.createStatement()){
            String query = "CREATE TABLE GENRE"
                    + "( id int NOT NULL,"
                    + "name varchar(30),"
                    + "PRIMARY KEY (id) )";
            stmt.executeQuery(query);
        } catch (SQLException e) {
            //Table exists
            e.printStackTrace();
        }
    }
    
    public void createMovie(){
        try (Statement stmt = connection.createStatement()){
            
            String query = "CREATE TABLE MOVIE"
                    + "( id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "tile varchar(50),"
                    + "price int,"
                    + "movie_year int,"
                    + "genre_id int,"
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (genre_id) REFERENCES GENRE(id) )";
            
            stmt.executeQuery(query);
            
        } catch (SQLException e) {
            //Table exists
            e.printStackTrace();
        }
    }
    
    public void createMember(){
        try (Statement stmt = connection.createStatement()){
            
            String query = "CREATE TABLE MEMBER"
                    + "( id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "username varchar(50) UNIQUE,"
                    + "password varchar(255),"
                    + "premium boolean,"
                    + "money int,"
                    + "admin_status boolean,"
                    + "PRIMARY KEY (id) )";
            
            stmt.executeQuery(query);
            
        } catch (SQLException e) {
            //Table exists            
            e.printStackTrace();
        }
    }
    
    public void createLoan(){
        try (Statement stmt = connection.createStatement()){
            
            String query = "CREATE TABLE MOVIE"
                    + "( id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "member_id int,"
                    + "movie_id int,"
                    + "return_status boolean,"
                    + "taken_date date,"
                    + "brought_date date,"
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (member_id) REFERENCES MEMBER(id),"
                    + "FOREIGN KEY (movie_id) REFERENCES MOVIE(id) )";
            
            stmt.executeQuery(query);
            
        } catch (SQLException e) {
            //Table exists
            e.printStackTrace();
        }
    }
}
