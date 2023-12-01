/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.gui;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import pl.polsl.model.User;
import pl.polsl.model.UserDao;

/**
 * Custom cell editor for buttons in a JTable. Extends DefaultCellEditor.
 * Provides button actions for buying and deleting movies associated with a user.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private long movieId;
    private long userId;
    private boolean isPushed;
    private UserDao userDao;
    
    /**
     * Constructs a ButtonEditor with a checkbox and a user ID.
     * 
     * @param checkBox The checkbox to use in the editor.
     * @param userId The ID of the user associated with the editor.
     */
    public ButtonEditor(JCheckBox checkBox, long userId) {
        super(checkBox);
        this.userId = userId;
        this.userDao = new UserDao();
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    /**
     * Gets the component used for editing a cell.
     * 
     * @param table The table containing the cell.
     * @param value The value of the cell.
     * @param isSelected True if the cell is selected, false otherwise.
     * @param row The row of the cell.
     * @param column The column of the cell.
     * @return The component for editing the cell.
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object movieIdObject = model.getValueAt(row, 0);
        if (movieIdObject instanceof Long) {
            movieId = (Long) movieIdObject;
        } 
                
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    /**
     * Gets the value of the cell editor.
     * 
     * @return The value of the cell editor.
     */
    public Object getCellEditorValue() {
        if (isPushed) {
            if ("Buy".equals(label)) {
                buyButtonClicked();
            }
            else if ("Delete".equals(label)) {
                deleteButtonClicked();
            }
        }
        isPushed = false;
        return label;
    }

    /**
     * Stops the cell editing.
     * 
     * @return True if editing was stopped successfully, false otherwise.
     */
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    /**
     * Fires the editing stopped event.
     */
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
    
    /**
     * Handles the action when the "Buy" button is clicked.
     * Attempts to buy a movie and displays a message accordingly.
     */
    public void buyButtonClicked(){
        User user = userDao.getUserById(userId);
        if (user == null) {
            JOptionPane.showMessageDialog(editorComponent, "User does not exist");
            return;
        }
        if (userDao.findUserMovie(user, movieId)) {
            JOptionPane.showMessageDialog(editorComponent, "You have that movie!");
        } else {
            if (userDao.buyMovie(user, movieId)){
                JOptionPane.showMessageDialog(editorComponent, "Purchase completed");
            } else {
                JOptionPane.showMessageDialog(editorComponent, "You do not have enough money!");
            }
        }
    }
    
    /**
     * Handles the action when the "Delete" button is clicked.
     * Attempts to delete a movie from the user's account and displays a message accordingly.
     */
    public void deleteButtonClicked() {
        User user = userDao.getUserById(userId);
        if (user == null) {
            JOptionPane.showMessageDialog(editorComponent, "User does not exist");
            return;
        }
        if (userDao.removeUserMovie(user, movieId)){
            JOptionPane.showMessageDialog(editorComponent, "Movie was deleted from your account");
        } else {
            JOptionPane.showMessageDialog(editorComponent, "You do not have that movie!");
        }
    }
}
