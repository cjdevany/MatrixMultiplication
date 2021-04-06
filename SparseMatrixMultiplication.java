import java.util.*;

class SparseMatrixMultiplication implements Runnable {
    private static double[] v;
    int row;
    static double[] result;
    static SparseMatrix A;

    public SparseMatrixMultiplication(double[] v, SparseMatrix A, int row) {
        this.v = v;
        this.A = A;
        this.row = row;
        result = new double[A.size()];
    }

    // Returns the product of a sparse matrix, A, with a vector v.
    public static double[] sparseMatrixMultiplication(SparseMatrix A, double[] v) {
        double[] sum = new double[v.length];
        ArrayList<ArrayList<Cell>> matrix = A.matrix;
        // Sparse matrix multiplication - Each cell in A is a Cell which is an (i, v) pair
        for (int i = 0; i < A.size(); i++) {
            for (Cell c : matrix.get(i)) {
                sum[i] += v[c.col] * c.value;
            }
        }
        return sum;
    }    
    
    public void run() {
        double rowSum = 0;
        // For each Cell in the sparse matrix, A, add the sum of the value of that
        // cell and the value of the vector, v, at at index.
        for (Cell c : A.getRow(row)) {
            rowSum += c.value * v[c.col];
        }
        result[row] = rowSum;
    }

    public double[] getResults() {
        return result;
    }


    public static void main(String[] args) {
        double[][] mat = {{2, -1, 0, 0}, {-1, 2, -1, 0}, {0, -1, 2, -1}, {0, 0, -1, 2}};
        double[] v = {1, 0, -1, 0};

        SparseMatrix A = new SparseMatrix(mat);
        // double[] test = sparseMatrixMultiplication(A, v);
        // // Test should equal {2, 0, -2, 1}.
        // System.out.println(Arrays.toString(test));

        // Create the array of thread objects.
        Thread[] t = new Thread[A.size()];

        // Initialize the thread array with SparseMatrixMultipliation objects.
        for(int i = 0; i < t.length; i++){
            t[i] = new Thread(new SparseMatrixMultiplication(v, A, i));
        }

        // Start each thread object.
        for(int i = 0; i < t.length; i++){
            t[i].start();
        }

        // Wait for the threads to die and join the results.
        try {
            for (int i = 0; i < t.length; i++) {
                t[i].join();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
