package ru.vsu.cs.vvp2020.g81.elfimov_k_d;

import ru.vsu.cs.vvp2020.g81.elfimov_k_d.util.SwingUtils;

import java.util.Locale;

public class Program {
    public static void main(String[]args) {
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameMain().setVisible(true);
            }
        });
    }
}
