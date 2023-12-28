/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Miczi
 */
@Data
public class Loan {
    
    private int id;
    private String title;
    private Date takenDate;
    private Date broughtDate;
    private boolean returnStatus;
}
