/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.video_rental_shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 *
 * @author Miczi
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    private boolean premium;
    private int balance;
    
}
