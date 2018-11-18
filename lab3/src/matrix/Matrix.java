package matrix;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int n;
    private int m;
    private List<List<Double>> matrix;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.matrix = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(m));
        }
    }

    public int getNumberOfRows() {
        return n;
    }

    public int getNumberOfColumns() {
        return m;
    }

    public double getItem(int i, int j) {
        if (i >= n || j >= m) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i).get(j);
    }

    public void setItem(int i, int j, double item) {
        if (i >= n || j >= m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(i).set(j, item);
    }

    public void addItem(int row, int column, double item) {
        if (row >= n || column >= m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(row).add(column, item);
        m = Math.max(m, matrix.get(row).size());
    }

    public void addItem(int row, double item) {
        if (row >= n) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(row).add(item);
        m = Math.max(m, matrix.get(row).size());
    }

    public void addRow(int i, List<Double> row) {
        if (row.size() != m || i > n) {
            throw new IndexOutOfBoundsException();
        }
        if (i < matrix.size()) {
            matrix.set(i, row);
        }
        n = Math.max(n, matrix.size());
    }

    public void addRow(List<Double> row) {
        if (row.size() != m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.add(row);
        n = Math.max(n, matrix.size());
    }

    public void addColumn(int numberOfColumn, List<Double> column) {
        if (column.size() > n  || numberOfColumn >= m) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < column.size(); ++i) {
            matrix.get(i).set(numberOfColumn, column.get(i));
        }
    }

    public List<Double> getColumn(int numberOfColumn) {
        if (numberOfColumn >= m) {
            throw new IndexOutOfBoundsException();
        }
        List<Double> column = new ArrayList<>(0);
        for (int i = 0; i < n; ++i) {
            column.add(getItem(i, numberOfColumn));
        }
        return column;
    }

    public List<Double> getRow(int i) {
        if (i >= matrix.size()) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i);
    }

    public void concat(Matrix other) {
        if (other.getNumberOfRows() != n) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < n; ++i) {
            List<Double> currentRow = matrix.get(i);
            for (int j = 0; j < other.getNumberOfColumns(); ++j) {
                currentRow.add(other.getItem(i, j));
            }
        }
        m += other.getNumberOfColumns();
    }
}