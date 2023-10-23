/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

/**
 *
 * @author Michal Lajczak
 * @version 1.0
 * 
 */
@Data
@Entity
public class Genre {
    @Id
    @GeneratedValue
    private int id;
  
    private String name;
}
