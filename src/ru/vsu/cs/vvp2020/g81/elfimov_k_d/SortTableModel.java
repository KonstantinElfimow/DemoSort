package ru.vsu.cs.vvp2020.g81.elfimov_k_d;

import javax.swing.table.AbstractTableModel;

public class SortTableModel extends AbstractTableModel implements StateViewer {
    private SortState state = null;

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        if (state == null)
            return 0;
        return state.getArray().length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        if (state == null)
            return null;
        return state.getArray()[c];
    }
    @Override
    public String getColumnName(int col) {
        return String.format("[%d]", col);
    }
    @Override
    public Class getColumnClass(int col) {
        return Integer.class;
    }
    @Override
    public void Show(SortState ss) {
        state = ss;
        fireTableStructureChanged();
    }

    public SortState getState() {
        return state;
    }
}
