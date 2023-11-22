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
 *
 * @author Miczi
 */
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private long movieId;
    private long userId;
    private boolean isPushed;
    private UserDao userDao;
    
    public ButtonEditor(JCheckBox checkBox, long userId) {
        super(checkBox);
        this.userId = userId;
        this.userDao = new UserDao();
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

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

    public Object getCellEditorValue() {
        if (isPushed) {
            if (label == "Buy") {
                buyButtonClicked();
            }
            else if (label == "Delete") {
                deleteButtonClicked();
            }
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
    
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
