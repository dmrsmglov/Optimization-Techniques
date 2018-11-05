package matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixCalculator {
    private Matrix matrix;

    public MatrixCalculator() {
        this.matrix = new Matrix(0, 0);
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void multiplicationSubRow(int row, int begin, int end, int x) {
        List<Double> matrixRow = matrix.getRow(row);
        for (int i = begin; i < end; ++i) {
            matrixRow.set(i, matrixRow.get(i) * x);
        }
    }

    public List<Double> divisionColumns(List<Double> first, List<Double> second) {
        List<Double> result = new ArrayList<>(0);
        for (int i = 0; i < first.size() - 1; ++i) {
            result.add(first.get(i) / (double) first.get(i));
        }
        result.add(first.get(first.size() - 1));
        return result;
    }

    public void divisionSubRowByItem(int row, int begin, int end, double item) {
        List<Double> matrixRow = matrix.getRow(row);
        for (int i = begin; i < end; ++i) {
            matrix.setItem(row, i, matrix.getItem(row, i) / item);
        }
    }

    public void additionSubRowWithSubRow(int firstRow, int secondRow, int begin, int end, int maxPos) {
        double x = matrix.getItem(firstRow, maxPos) * (-1);
        for (int i = begin; i < end; ++i) {
            double a = matrix.getItem(firstRow, i);
            double b = matrix.getItem(secondRow, i);
            double item = a + b * x;
            matrix.setItem(firstRow, i, item);
        }
    }

    public int findPositiveMaxFromSubRow(int row, int begin, int end) {
        int pos = -1;
        double maxValue = 0;
        List<Double> matrixRow = matrix.getRow(row);
        for (int i = begin; i < end; ++i) {
            if (matrixRow.get(i) > 0) {
                if (pos == -1 || maxValue < matrixRow.get(i)) {
                    pos = i;
                    maxValue = matrixRow.get(i);
                }
            }
        }
        return pos;
    }

    public int findPositiveMinFromSubColumn(int column, int begin, int end) {
        int pos = -1;
        double minValue = Integer.MAX_VALUE / 2;
        List<Double> matrixColumn = matrix.getColumn(column);
        for (int i = begin; i < end; ++i) {
            if (matrixColumn.get(i) > 0) {
                if (pos == -1 || minValue > matrixColumn.get(i)) {
                    pos = i;
                    minValue = matrixColumn.get(i);
                }
            }
        }
        return pos;
    }
}