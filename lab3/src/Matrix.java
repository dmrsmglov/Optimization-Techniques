import javafx.util.Pair;

import java.util.ArrayList;

public class Matrix {
    private int n;
    private int m;
    private ArrayList<ArrayList<Integer>> matrix;

    Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.matrix = new ArrayList<>(n);
    }

    void addRow(int i, ArrayList<Integer> row) {
        if (i >= n || row.size() != m) {
            throw new IndexOutOfBoundsException();
        }
        if (i < matrix.size()) {
            matrix.set(i, row);
        }
        else {
            matrix.add(row);
        }
    }

    void addRow(ArrayList<Integer> row) {
        if (row.size() != m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.add(row);
    }

    void addColumn(ArrayList<Integer> column) {
        if (column.size() != matrix.size()) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < column.size(); ++i) {
            matrix.get(i).add(column.get(i));
        }
    }

    ArrayList<Integer> getRow(int i) {
        if (i >= matrix.size()) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i);
    }
}
