package matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixReaderCreator {

    public Matrix readAndCreate(String filename) {
        Matrix matrix = new Matrix(0, 0);
        try (Scanner sc = new Scanner(new File(filename))) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            matrix = new Matrix(n, m);
            for (int i = 0; i < n; ++i) {
                List<Double> row = new ArrayList<>();
                for (int j = 0; j < m; ++j) {
                    row.add(sc.nextDouble());
                }
                matrix.addRow(i, row);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File " + filename + " not found.\n");
        }
        return matrix;
    }
}