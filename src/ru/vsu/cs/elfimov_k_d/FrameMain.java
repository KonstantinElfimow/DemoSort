package ru.vsu.cs.elfimov_k_d;
import org.jetbrains.annotations.Nullable;
import ru.vsu.cs.elfimov_k_d.util.ArrayUtils;
import ru.vsu.cs.elfimov_k_d.util.JTableUtils;
import ru.vsu.cs.elfimov_k_d.util.SwingUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FrameMain extends JFrame {
    private JPanel contentPane;
    private JButton prevButton;
    private JButton nextButton;
    private JSlider currentStateSlider;
    private JTextField periodValue;
    private JCheckBox cyclicCB;
    private JButton playButton;
    private JButton resetButton;
    private JScrollPane inputTableScrollPanel;
    private JTable inputTable;
    private JButton solveButton;
    private JButton createArrayButton;
    private JSpinner arraySizeSpinner;
    private JPanel resultPanel;
    private JCheckBox JTableCheckBox;
    private JCheckBox graphics2DCheckBox;
    private Timer timer;
    private SortStateManager manager = new SortStateManager();
    private StateViewer viewer;

    private enum ModeType {Init, Manual, Auto}
    private void setControlsEnable(ModeType mode) {
        createArrayButton.setEnabled(mode != ModeType.Auto);
        inputTable.setEnabled(mode != ModeType.Auto);
        solveButton.setEnabled(mode != ModeType.Auto);
        playButton.setEnabled(mode != ModeType.Init);
        resetButton.setEnabled(mode == ModeType.Manual);
        cyclicCB.setEnabled(mode != ModeType.Manual);
        prevButton.setEnabled(mode == ModeType.Manual);
        nextButton.setEnabled(mode == ModeType.Manual);
        currentStateSlider.setEnabled(mode == ModeType.Manual);
        JTableCheckBox.setEnabled(mode == ModeType.Init);
        graphics2DCheckBox.setEnabled(mode == ModeType.Init);
    }

    public FrameMain() {
        this.setTitle("Task_4");
        this.setContentPane(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        JTableUtils.initJTableForArray(inputTable, 100, false,
                true, false, true);
        inputTable.setRowHeight(25);
        JTableUtils.writeArrayToJTable(inputTable, new int[]{1, 2, 3, 4, 5});
        arraySizeSpinner.setModel(new SpinnerNumberModel(10, 1, 100, 1));
        setControlsEnable(ModeType.Init);

        graphics2DCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (graphics2DCheckBox.isSelected()) {
                    JTableCheckBox.setEnabled(false);
                    SortDrawPanel panel = new SortDrawPanel();
                    viewer = panel;
                    resultPanel.setLayout(new GridLayout());
                    resultPanel.add(new JScrollPane(panel));
                }
            }
        });
        JTableCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (JTableCheckBox.isSelected()) {
                    graphics2DCheckBox.setEnabled(false);
                    SortTableModel tm = new SortTableModel();
                    viewer = tm;
                    JTable outputTable = new JTable(tm);
                    outputTable.setRowHeight(25);
                    outputTable.setDefaultRenderer(Integer.class, new SortCellRenderer());
                    resultPanel.setLayout(new GridLayout());
                    resultPanel.add(new JScrollPane(outputTable));
                }
            }
        });

        createArrayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] array = ArrayUtils.createRandomIntArray((Integer)arraySizeSpinner.getValue(), 100);
                JTableUtils.writeArrayToJTable(inputTable, array);
            }
        });

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int[] array = JTableUtils.readIntArrayFromJTable(inputTable);
                    assert array != null;
                    manager.setStates(Sorting.insertionSort(array));
                    setControlsEnable(ModeType.Manual);
                } catch (ParseException e) {
                    SwingUtils.showInfoMessageBox("Не удалось ввести массив", "Ошибка");
                    e.printStackTrace();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                manager.reset();
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                manager.prev();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                manager.next();
            }
        });

        currentStateSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                manager.setCurrentIndex(currentStateSlider.getValue());
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                manager.next();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (timer.isRunning()) {
                    timer.stop();
                    playButton.setText("Запустить");
                    setControlsEnable(ModeType.Manual);
                } else {
                    int delay = 0;
                    try {
                        delay = Integer.parseInt(periodValue.getText());
                    } catch (NumberFormatException ex) {
                        SwingUtils.showInfoMessageBox("Не удалось прочитать значение периода", "Ошибка");
                        ex.printStackTrace();
                        return;
                    }
                    if (delay < 0) {
                        SwingUtils.showInfoMessageBox("Период не может быть отрицательным", "Ошибка");
                        return;
                    }
                    playButton.setText("Остановить");
                    setControlsEnable(ModeType.Auto);
                    manager.setCyclic(cyclicCB.isSelected());
                    timer.setDelay(delay);
                    timer.setInitialDelay(delay);
                    timer.start();
                }
            }
        });

        manager.setListener(new SortStateManager.SortStateChangedListener() {
            @Override
            public void stateChanged(@Nullable SortState state, int index, int total) {
                setTitle(String.format("%d/%d", index + 1, total));
                currentStateSlider.setMinimum(0);
                currentStateSlider.setMaximum(total - 1);
                currentStateSlider.setValue(index);
                viewer.Show(state);
            }

            @Override
            public void finished() {
                timer.stop();
                playButton.setText("Запустить");
                setControlsEnable(ModeType.Manual);
            }
        });
    }
}
