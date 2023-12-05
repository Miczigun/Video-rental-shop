/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Represents a genre entity with an identifier and a name.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
@Data
@Entity
public class Genre{

    /**
     * The unique identifier for the genre.
     */
    @Id
    private int id;

    /**
     * The name of the genre.
     */
    private String name;
}

