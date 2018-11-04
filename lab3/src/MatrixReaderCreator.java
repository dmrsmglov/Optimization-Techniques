import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrixReaderCreator {

    Matrix readAndCreate(String filename) {
        Matrix matrix = new Matrix(0, 0);
        try (Scanner sc = new Scanner(new File(filename))) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            matrix = new Matrix(n, m);
            for (int i = 0; i < n; ++i) {
                ArrayList<Integer> row = new ArrayList<>(m);
                for (int j = 0; j < m; ++j) {
                    row.set(j, sc.nextInt());
                }
                matrix.addRow(row);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + filename + " not found.\n");
        }
        return matrix;
    }
}