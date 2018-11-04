public class MatrixPrinter {
    void printToSout(Matrix matrixToPrint) {
        int n = matrixToPrint.getNumberOfRows();
        int m = matrixToPrint.getNumberOfColumns();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                System.out.print(matrixToPrint.getItem(i, j) + " ");
            }
            System.out.println();
        }
    }

}
