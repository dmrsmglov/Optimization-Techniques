import java.util.ArrayList;

public class Matrix {
    private int n;
    private int m;
    private ArrayList<ArrayList<Integer>> matrix;

    Matrix(int n, int m){
        this.n = n;
        this.m = m;
        this.matrix = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            matrix.add(new ArrayList<>(m));
        }
    }
}
