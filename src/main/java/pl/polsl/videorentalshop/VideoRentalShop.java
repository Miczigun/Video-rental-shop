/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.videorentalshop;

import pl.polsl.model.UserDao;
import pl.polsl.gui.MainFrame;

/**
 *
 * @author Miczi
 */
public class VideoRentalShop {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        UserDao userController = new UserDao();
        mainFrame.setUserController(userController); // Pass the UserDao to the MainFrame
        mainFrame.setVisible(true);
    }
}
