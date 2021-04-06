import java.util.*;

class DenseMatrixMultiplication implements Runnable {
    private static double [] v;
    int row;
    static double [] result;
    static DenseMatrix A;

    public DenseMatrixMultiplication(double [] v, DenseMatrix A, int row) {
        this.v = v;
        this.A = A;
        this.row = row;
        result = new double[A.size()];
    }

    //Returns the product of a dense matrix A with a vector V.
    public static double[] denseMatrixMultiplication(DenseMatrix A, double [] v) {
        double [] sum = new double[v.length];
        double [][] table = A.toTable();
        for(int i = 0; i < A.size(); i++) {
            for(int j = 0; j < A.size(); j++) {
                sum[i] += v[j] * table[i][j];
            }
        }
        return sum;
    }

    public void run() {
        double rowSum = 0;
        double [] table = A.getRow(row);
        // For each Cell in the dense matrix, A, add the sum of the value of that
        // cell and the value of the vector, v, at at index.
        for(int j = 0; j < A.size(); j++) {
            rowSum += v[j] * table[j];
        }
        result[row] = rowSum;
    }

    public static void main(String[] args) {
        
    }
}
