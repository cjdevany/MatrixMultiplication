import java.util.*;

public class DenseMatrix {
    double [][] matrix;

    public DenseMatrix(int size) {
        this.matrix = new double [size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    public DenseMatrix(double [][] matrix) {
        this.matrix = matrix;
    }

    public DenseMatrix(SparseMatrix table) {
        this.matrix = table.toTable();
    }

    public int size() {
        return this.matrix.length;
    }

    public double [][] toTable() {
        return this.matrix;
    }

    public double [] getRow(int row) {
        return this.matrix[row];
    }

    public void print() {
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        
    }
}
