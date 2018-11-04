import java.util.ArrayList;
import java.util.Collections;

class SimplexMethod {
    private Matrix a = new Matrix(0, 0);
    private Matrix b = new Matrix(0, 0);
    private Matrix c = new Matrix(0, 0);

    private MatrixPrinter matrixPrinter = new MatrixPrinter();
    private MatrixReaderCreator matrixCreator = new MatrixReaderCreator();
    private MatrixCalculator matrixCalculator = new MatrixCalculator();

    private Matrix matrix = new Matrix(0, 0);

    private void createMatrixForExecution() {
        String fileA = "A";
        String fileB = "B";
        String fileC = "C";
        String fileE = "E";
        a = matrixCreator.readAndCreate(fileA);
        b = matrixCreator.readAndCreate(fileB);
        c = matrixCreator.readAndCreate(fileC);
        Matrix e = matrixCreator.readAndCreate(fileE);
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

            ArrayList<Double> currentColumn = matrix.getColumn(maxPos);
            ArrayList<Double> lastColumn = matrix.getColumn(matrix.getNumberOfColumns() - 1);
            ArrayList<Double> modifiedLastColumn = matrixCalculator.divisionColumns(lastColumn, currentColumn);
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
        matrixPrinter.printToSout(matrix);
    }
}