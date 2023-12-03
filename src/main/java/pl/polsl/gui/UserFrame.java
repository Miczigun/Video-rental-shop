/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pl.polsl.gui;

import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pl.polsl.model.GenreDao;
import pl.polsl.model.MovieDao;
import pl.polsl.model.UserDao;
import pl.polsl.model.Movie;
import pl.polsl.model.User;

/**
 * The UserFrame class represents the main user interface for interacting with the movie database.
 * Users can view, purchase, and manage movies based on their preferences and account status.
 * The frame includes options for viewing all movies, user-specific movies, filtering by genre,
 * purchasing premium access, topping up the wallet, and logging out. It also displays the user's
 * current status (Standard or Premium) and wallet balance.
 *
 * @author Michal Lajczak
 * @version 1.2
 */
public class UserFrame extends javax.swing.JFrame {
    private long userId;
    private MovieDao movieController;
    private UserDao userController;
    private GenreDao genreController;
    private MainFrame mainFrame;
    /**
     * Creates new form UserFrame
     */
    public UserFrame(MainFrame mainFrame, long userId) {
        this.userId = userId;
        this.mainFrame = mainFrame;
        this.genreController = new GenreDao();
        this.movieController = new MovieDao();
        this.userController = new UserDao();
        initComponents();
        displayMovies();
        setStatusUser();;
    }
    public UserFrame() {
        this.movieController = new MovieDao();
        this.userController = new UserDao();
        initComponents();
        displayMovies();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        List<String> genreNames = genreController.getGenres();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Movies");
        jButton1.setToolTipText("Click to view movies");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Your Movies");
        jButton2.setToolTipText("Click to view your movies");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Logout");
        jButton3.setToolTipText("Click to log out from your account");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(userController.getUserById(userId).getUsername());
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton4.setText("Top up wallet");
        jButton4.setToolTipText("Click to add funds to your wallet");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Wallet: " + userController.getUserById(userId).getBalance());
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Standard User");

        jButton6.setText("Buy Premium");
        jButton6.setToolTipText("Click to purchase premium access");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id","Title", "Genre", "Year", "Price", ""
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(genreNames.toArray(new String[0])));
        jComboBox1.setToolTipText("Select your genre");

        jButton5.setText("Search");
        jButton5.setToolTipText("Click to perform a search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(261, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(311, 311, 311))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Event handler for the "Sign Up" button.
     * Shows the main frame and disposes of the current frame.
     */    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        mainFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
     /**
     * Sets the user status label based on whether the user is premium or standard.
     */   
    public void setStatusUser(){
        User user = userController.getUserById(userId);
        if(user.getPremium()){
            jLabel3.setText("Premium User");
        }
        else{
            jLabel3.setText("Standard User");
        }
    }
    /**
     * Event handler for the "Sign In" button.
     * Fetches the list of movies from the database and populates the table.
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Fetch the list of movies from the database using movieController
        List<Movie> movies = movieController.getAllMovies();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Movie movie : movies) {
            Object[] row = { movie.getId(), movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPrice(), "Buy" };

            // Set the movie ID as a hidden value in the last column (index 4)
             // Assuming getId() retrieves the movie ID
            model.addRow(row);
        }

        // Add a custom renderer for the "Buy" column to display a button
        //jTable1.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

        // Add a custom editor for the "Buy" column to handle button click
        //jTable1.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Buy"));
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * Event handler for the "Show My Movies" button.
     * Fetches the list of movies owned by the user and populates the table.
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        List<Movie> userMovies = userController.userMovies(userId);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows
        
        for (Movie movie : userMovies) {
            Object[] row = { movie.getId(), movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPrice(), "Delete" };
            
            model.addRow(row);
        }
        
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), userId));
    }//GEN-LAST:event_jButton2ActionPerformed
    /**
     * Event handler for the "Top Up Account" button.
     * Allows the user to top up their account balance.
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        User user = userController.getUserById(userId);
        String value = JOptionPane.showInputDialog("How much do you want to top up your account for?");
        if (value != null){
            try {
                int wallet = Integer.parseInt(value);
                if (wallet > 0 ){
                    userController.topUpTheAccount(user.getId(), wallet);
                    user = userController.getUserById(user.getId());
                    jLabel2.setText("Wallet: " + user.getBalance());
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Value must be greater than zero!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed
    /**
     * Event handler for the "Filter by Genre" button.
     * Fetches movies by the selected genre and populates the table.
     */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        List<Movie> movies = movieController.getMoviesByGenre(jComboBox1.getSelectedItem().toString());
        
        if (movies.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "We do not have movie from this genre");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Movie movie : movies) {
            Object[] row = { movie.getId(), movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPrice(), "Buy" };
           
            model.addRow(row);
            
        }

        // Add a custom renderer for the "Buy" column to display a button
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());

        // Add a custom editor for the "Buy" column to handle button click
        jTable1.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), userId));
    }//GEN-LAST:event_jButton5ActionPerformed
    /**
     * Event handler for the "Purchase Premium" button.
     * Allows the user to buy premium status.
     */
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        User user = userController.getUserById(userId);
        if (userController.buyPremium(user)) {
            JOptionPane.showMessageDialog(rootPane, "Thank you for purchasing premium");
            jLabel3.setText("Premium User");
            user = userController.getUserById(user.getId());
            jLabel2.setText("Wallet: " + user.getBalance());
        } else {
            JOptionPane.showMessageDialog(rootPane, "You do not have enough money or you have premium!");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * Displays all movies in the table.
     */    
    private void displayMovies() {
        // Fetch the list of movies from the database using movieController
        List<Movie> movies = movieController.getAllMovies();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Movie movie : movies) {
            Object[] row = { movie.getId(), movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getPrice(), "Buy" };
           
            model.addRow(row);
            
        }

        // Add a custom renderer for the "Buy" column to display a button
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());

        // Add a custom editor for the "Buy" column to handle button click
        jTable1.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), userId));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
