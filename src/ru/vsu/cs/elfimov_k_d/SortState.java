package ru.vsu.cs.elfimov_k_d;

import java.util.Arrays;

public class SortState {
    public enum Type {State, Compare, Change}
    private Type type;
    private int[] array;
    private int left, right;
    private int a, b;

    public SortState(Type type, int[] array, int left, int right, int a, int b) {
        this.type = type;
        this.array = Arrays.copyOf(array, array.length);
        this.left = left;
        this.right = right;
        this.a = a;
        this.b = b;
    }

    public int getB() {
        return b;
    }
    public int getA() {
        return a;
    }
    public int getRight() {
        return right;
    }
    public int getLeft() {
        return left;
    }
    public int[] getArray() {
        return array;
    }
    public Type getType() {
        return type;
    }
}
