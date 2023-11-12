/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import at.favre.lib.crypto.bcrypt.BCrypt;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Michal Lajczak
 * @version 1.1
 */
public class UserModelTest {
    
    
    /**
     * Test case to ensure that the setPassword method sets a hashed password.
     */
    @Test
    void setPassword_ShouldSetHashedPassword() {
        User user = new User();
        user.setPassword("password123");

        assertNotNull(user.getPassword());
        assertNotEquals("password123", user.getPassword());
    }
    
    /**
     * Test case to verify that the getPremium method returns the correct premium status.
     */
    @Test
    void getPremium_ShouldReturnPremiumStatus() {
        User user = new User();
        assertFalse(user.getPremium());

        user.setPremium(true);
        assertTrue(user.getPremium());
    }
    
    /**
     * Parameterized test case to set a hashed password with different inputs.
     *
     * @param password The password input for testing.
     */

    @ParameterizedTest
    @ValueSource(strings = {"password123", "securePass", "testPassword"})
    void setPassword_ShouldSetHashedPassword(String password) {
        User user = new User();
        user.setPassword(password);

        assertTrue(isValidBCryptPassword(password, user.getPassword()));
    }
    
    /**
     * Helper method to check if a hashed password is valid using BCrypt.
     *
     * @param plainTextPassword The plain text password.
     * @param hashedPassword    The hashed password to verify.
     * @return True if the password is valid; false otherwise.
     */
    private boolean isValidBCryptPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword).verified;
    }
}    

