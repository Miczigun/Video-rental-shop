/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miczi
 */
@Data
public class Movie {
    private String title;
    private String genre;
    private int year;
    private int price;
}
