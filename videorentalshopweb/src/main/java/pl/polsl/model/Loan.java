/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.util.Date;
import lombok.Data;

/**
 * Represents a loan of a movie by a user in the video rental shop.
 * <p>
 * This class encapsulates information about the loan, including its unique identifier,
 * the title of the movie, the date it was taken, the date it was brought back,
 * and the return status indicating whether the movie has been returned.
 * </p>
 *
 * @author Michal Lajczak
 * @version 1.5
 */
@Data
public class Loan {

    /**
     * The unique identifier for the loan.
     */
    private int id;

    /**
     * The title of the movie associated with the loan.
     */
    private String title;

    /**
     * The date when the movie was taken on loan.
     */
    private Date takenDate;

    /**
     * The date when the movie was brought back.
     */
    private Date broughtDate;

    /**
     * The return status indicating whether the movie has been returned.
     */
    private boolean returnStatus;
}

