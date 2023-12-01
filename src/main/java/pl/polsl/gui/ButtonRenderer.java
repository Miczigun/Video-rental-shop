/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.gui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * Custom cell renderer for buttons in a JTable. Extends JButton and implements TableCellRenderer.
 * Provides rendering properties for buttons in a JTable.
 * 
 * @author Michal Lajczak
 * @version 1.2
 */
class ButtonRenderer extends JButton implements TableCellRenderer {
    
    /**
     * Constructs a ButtonRenderer.
     * Sets the component to be opaque.
     */
    public ButtonRenderer() {
        setOpaque(true);
    }   

    /**
     * Gets the component used for rendering a cell.
     * 
     * @param table The table containing the cell.
     * @param value The value of the cell.
     * @param isSelected True if the cell is selected, false otherwise.
     * @param hasFocus True if the cell has focus, false otherwise.
     * @param row The row of the cell.
     * @param column The column of the cell.
     * @return The component for rendering the cell.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
