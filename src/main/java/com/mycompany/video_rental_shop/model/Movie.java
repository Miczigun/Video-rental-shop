/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.video_rental_shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author Miczi
 */
@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private int price;
    private float rating;
    @Column(columnDefinition = "YEAR")
    private int year;
    @ManyToOne
    private Genre genre;
}
