import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SimplexMethod {

    private MatrixReaderCreator matrixCreator = new MatrixReaderCreator();
    private MatrixCalculator matrixCalculator = new MatrixCalculator();

    private Matrix matrix = new Matrix(0, 0);

    private void createMatrixForExecution() {
        String fileNameA = "A";
        String fileNameB = "B";
        String fileNameC = "C";
        String fileNameE = "E";

        Matrix a = matrixCreator.readAndCreate(fileNameA);
        Matrix b = matrixCreator.readAndCreate(fileNameB);
        Matrix c = matrixCreator.readAndCreate(fileNameC);
        Matrix e = matrixCreator.readAndCreate(fileNameE);

        matrix = new Matrix(a.getNumberOfRows(), 1);

        for (int i = 0; i < a.getNumberOfRows(); ++i) {
            matrix.addRow(i, new ArrayList<>(Collections.singletonList(a.getNumberOfColumns() + i + .0)));
        }
        a.concat(e);
        a.concat(b);
        matrix.concat(a);
        c.addItem(0, 0, 0);
        for (int i = e.getNumberOfColumns(); i < e.getNumberOfColumns() + e.getNumberOfRows() + 1; ++i) {
            c.addItem(0, 0);
        }
        matrix.addRow(c.getRow(0));
    }

    void execute() {
        createMatrixForExecution();
        matrixCalculator.setMatrix(matrix);
        matrixCalculator.multiplicationSubRow(matrix.getNumberOfRows() - 1, 1,
                matrix.getNumberOfColumns(), -1);

        while (true) {
            int maxPos = matrixCalculator.findPositiveMaxFromSubRow(matrix.getNumberOfRows() - 1, 1,
                    matrix.getNumberOfColumns() - 1);
            if (maxPos == -1) {
                break;
            }

            List<Double> currentColumn = matrix.getColumn(maxPos);
            List<Double> lastColumn = matrix.getColumn(matrix.getNumberOfColumns() - 1);
            List<Double> modifiedLastColumn = matrixCalculator.divisionColumns(lastColumn, currentColumn);

            matrix.addColumn(matrix.getNumberOfColumns() - 1, modifiedLastColumn);
            int minPos = matrixCalculator.findPositiveMinFromSubColumn(matrix.getNumberOfColumns() - 1, 0, matrix.getNumberOfRows());
            matrix.addColumn(matrix.getNumberOfColumns() - 1, lastColumn);

            matrix.setItem(minPos, 0, maxPos - 1);

            matrixCalculator.divisionSubRowByItem(minPos, 1, matrix.getNumberOfColumns(), matrix.getItem(minPos, maxPos));

            for (int i = 0; i < matrix.getNumberOfRows(); ++i) {
                if (i != minPos) {
                    matrixCalculator.additionSubRowWithSubRow(i, minPos, 1, matrix.getNumberOfColumns(), maxPos);
                }
            }
        }
    }

    void printResultBasis() {
        List<Double> resultBasis = new ArrayList<>(matrix.getNumberOfColumns() / 2 - 1);
        for (int i = 0; i < matrix.getNumberOfColumns() / 2; ++i) {
            resultBasis.add(0.);
        }
        for (int i = 0; i < matrix.getNumberOfColumns() / 2 - 1; ++i) {
            if (matrix.getItem(i, 0) < matrix.getNumberOfColumns() / 2) {
                int index = (int)matrix.getItem(i, 0);
                double value = matrix.getItem(i, matrix.getNumberOfColumns() - 1);
                resultBasis.set(index, value);
            }
        }
        System.out.println(resultBasis);
    }

    void printMinimum() {
        System.out.println(matrix.getItem(matrix.getNumberOfRows() - 1, matrix.getNumberOfColumns() - 1));
    }
}