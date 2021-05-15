package ru.vsu.cs.vvp2020.g81.elfimov_k_d;

import javax.swing.*;
import java.awt.*;

public class SortDrawPanel extends JPanel implements StateViewer {
    private SortState state = null;

    @Override
    public void Show(SortState ss) {
        state = ss;
        if (state != null) {
            this.setPreferredSize(new Dimension(state.getArray().length * 50 + 300, 300));
        }
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (state == null)
            return;
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        CellDrawer2D.drawState(state, gr, new Point(30, 30));
    }
}
