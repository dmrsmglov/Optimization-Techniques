import java.util.ArrayList;

class Matrix {
    private int n;
    private int m;
    private ArrayList<ArrayList<Integer>> matrix;

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

    int getItem(int i, int j) {
        if (i >= n || j >= m) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i).get(j);
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
            n++;
        }
    }

    void addRow(ArrayList<Integer> row) {
        if (row.size() != m) {
            throw new IndexOutOfBoundsException();
        }
        matrix.add(row);
        n++;
    }

    void addColumn(ArrayList<Integer> column) {
        if (column.size() != matrix.size()) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < column.size(); ++i) {
            matrix.get(i).add(column.get(i));
        }
        m++;
    }

    ArrayList<Integer> getRow(int i) {
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
            ArrayList<Integer> currentRow = matrix.get(i);
            for (int j = 0; j < other.getNumberOfColumns(); ++j) {
                currentRow.add(other.getItem(i, j));
            }
        }
        m += other.getNumberOfColumns();
    }
}
