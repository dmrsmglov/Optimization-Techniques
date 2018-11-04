import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class SimplexMethod {
    private String fileA = "A";
    private String fileB = "B";
    private String fileC = "C";
    private String fileE = "E";

    private Matrix a = new Matrix(0, 0);
    private Matrix b = new Matrix(0, 0);
    private Matrix c = new Matrix(0, 0);
    private MatrixPrinter matrixPrinter = new MatrixPrinter();
    private MatrixReaderCreator matrixCreator = new MatrixReaderCreator();

    private Matrix matrix = new Matrix(0, 0);

    private void createMatrixForExecution() {
        matrix = new Matrix(a.getNumberOfRows(), 1);
        for (int i = 0; i < a.getNumberOfRows(); ++i) {
            matrix.addRow(i, new ArrayList<>(Collections.singletonList(a.getNumberOfColumns() + i)));
        }
    }

    void execute() {
        a = matrixCreator.readAndCreate(fileA);
        b = matrixCreator.readAndCreate(fileB);
        c = matrixCreator.readAndCreate(fileC);
        Matrix e = matrixCreator.readAndCreate(fileE);

        createMatrixForExecution();

        a.concat(e);
        a.concat(b);
        matrix.concat(a);
        c.addItem(0, 0, 0);
        for (int i = e.getNumberOfColumns(); i < e.getNumberOfColumns() + e.getNumberOfRows() + 1; ++i) {
            c.addItem(0,0);
        }
        matrix.addRow(c.getRow(0));

        matrixPrinter.printToSout(matrix);
    }


}
