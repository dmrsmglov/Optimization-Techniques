import java.util.ArrayList;

class Matrix {
    private int n;
    private int m;
    private ArrayList<ArrayList<Double>> matrix;

    Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.matrix = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(m));
        }
    }

    int getNumberOfRows() {
        return n;
    }

    int getNumberOfColumns() {
        return m;
    }

    double getItem(int i, int j) {
        if (i >= n || j >= m) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i).get(j);
    }

    void setItem(int i, int j, double item) {
        if (i >= n || j >= m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(i).set(j, item);
    }

    void addItem(int row, int column, double item) {
        if (row >= n || column >= m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(row).add(column, item);
        m++;
    }

    void addItem(int row, double item) {
        if (row >= n) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(row).add(item);
        m++;
    }

    void addRow(int i, ArrayList<Double> row) {
        if (row.size() != m) {
            throw new IndexOutOfBoundsException();
        }
        if (i < matrix.size()) {
            matrix.set(i, row);
        }
        else {
            matrix.add(row);
            n++;
        }
    }

    void addRow(ArrayList<Double> row) {
        if (row.size() != m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.add(row);
        n++;
    }

    void addColumn(int numberOfColumn, ArrayList<Double> column) {
        if (column.size() != matrix.size() || numberOfColumn >= m) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < column.size(); ++i) {
            matrix.get(i).set(numberOfColumn, column.get(i));
        }
    }

    ArrayList<Double> getColumn(int numberOfColumn) {
        if (numberOfColumn >= m) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Double> column = new ArrayList<>(0);
        for (int i = 0; i < n; ++i) {
            column.add(getItem(i, numberOfColumn));
        }
        return column;
    }

    ArrayList<Double> getRow(int i) {
        if (i >= matrix.size()) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i);
    }


    void concat(Matrix other) {
        if (other.getNumberOfRows() != n) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < n; ++i) {
            ArrayList<Double> currentRow = matrix.get(i);
            for (int j = 0; j < other.getNumberOfColumns(); ++j) {
                currentRow.add(other.getItem(i, j));
            }
        }
        m += other.getNumberOfColumns();
    }
}