package ru.vsu.cs.vvp2020.g81.elfimov_k_d;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class SortCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );
        SortState state = ((SortTableModel) table.getModel()).getState();
        Color bg = Color.WHITE;
        if (column <= state.getLeft() || column >= state.getRight())
            bg = Color.GRAY;
        else if (column == state.getA() || column == state.getB()) {
            if (state.getType() == SortState.Type.Compare)
                bg = Color.YELLOW;
            else if (state.getType() == SortState.Type.Change)
                bg = Color.RED;
        }
        component.setBackground(bg);
        return component;
    }
}
