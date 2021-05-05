package ru.vsu.cs.vvp2020.g81.elfimov_k_d;

import java.util.ArrayList;
import java.util.List;

public class Sorting {
    public static List<SortState> insertionSort(int[] array) {
        List<SortState> states = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                states.add(new SortState(SortState.Type.Compare, array, -1, 0, 0, 0));
                continue;
            }
            states.add(new SortState(SortState.Type.Compare, array, -1, i, i - 1, i));
            if (array[i] < array[i - 1]) {
                int t = i;
                while (t > 0) {
                    if (array[t] >= array[t - 1]) {
                        states.add(new SortState(SortState.Type.Change, array, -1, i + 1, t, t));
                        break;
                    }
                    states.add(new SortState(SortState.Type.Change, array, -1, i + 1, t - 1, t));
                    swap(array, t);
                    t--;
                    if (t == 0) {
                        states.add(new SortState(SortState.Type.Change, array, -1, i + 1, 0, 0));
                    }
                }
            }
            states.add(new SortState(SortState.Type.State, array, -1, i + 1, 0, 0));
        }

        return states;
    }
    private static void swap(int[] array, int i) {
        int temp = array[i];
        array[i] = array[i - 1];
        array[i - 1] = temp;
    }
}
