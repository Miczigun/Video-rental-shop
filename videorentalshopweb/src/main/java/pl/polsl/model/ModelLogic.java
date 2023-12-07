/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miczi
 */
@Getter
@Setter
public class ModelLogic {
    
    private List<Movie> movies = new ArrayList<Movie>();
    private List<User> users;
    
    public ModelLogic(){
        loadMovies();
    }
    
    public void loadMovies(){
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            
            File file = new File("C:\\Users\\micha\\Desktop\\studia\\sem5\\JwIiUM\\Projekt\\Video-rental-shop\\videorentalshopweb\\movies.json");
            movies = objectMapper.readValue(file, new TypeReference<List<Movie>>() {});
            
        } catch (IOException e) {
            
        }
    }
    
    public List<Movie> getMovies(){
        return movies;
    }
}
