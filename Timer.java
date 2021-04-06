import java.util.*;

public class Timer {
    static Random r = new Random();

    //returns a random double between -10 and 10
    public static double getRandomNumber() {
        return Math.floor(-10 + 20 * r.nextDouble());
    }

    //generates a sparse array where sparcity is a value between 0 and 1 indicating the non-zero %
    public static double [][] generateSparseTable(int size, double sparsity) {
        int numZeroes = (int)((size * size) * (sparsity));
        double [][] matrix = new double[size][size];
        
        //tracks if we've placed a zero at a spot or not
        // When a spot is true that spot has been set to zero.
        boolean [][] zeroed = new boolean[size][size];
        
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                double randomNum = getRandomNumber();
                matrix[i][j] = randomNum;
            }
        }

        while(numZeroes > 0){
            int randomRow = r.nextInt(size);
            int randomCol = r.nextInt(size);

            // While the random spot has been set to zero
            while(zeroed[randomRow][randomCol]) {
                // Get a new spot
                randomRow = r.nextInt(size);
                randomCol = r.nextInt(size);
                // Set that spot equal to zero
                if(!zeroed[randomRow][randomCol]) {
                    zeroed[randomRow][randomCol] = true;
                    matrix[randomRow][randomCol] = 0;
                    numZeroes--;
                    break;
                }
            }
            if(!zeroed[randomRow][randomCol]) {
                zeroed[randomRow][randomCol] = true;
                matrix[randomRow][randomCol] = 0;
                numZeroes--;
            }
        }
        return matrix;
    }

    //generates a dense matrix of random doubles between -10 and 10
    public static double [][] generateDenseTable(int size) {
        double [][] matrix = new double [size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                double randomNum = getRandomNumber();
                matrix[i][j] = randomNum;
            }
        }

        return matrix;
    }

    // Generates a double vector of a given size containing values in the range [-10, 10].
    public static double [] generateVector(int size) {
        double [] vector = new double[size];
        for (int i = 0; i < size; i++) {
            vector [i] = getRandomNumber();
        }
        return vector;
    }

    // Times the multithreaded version of sparse matrix multiplation.
    public static long timeSparse(double [][] A, double[] v) {
        SparseMatrix matrix = new SparseMatrix(A);
        long start = System.nanoTime();
        
        Thread[] t = new Thread[A.length];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new SparseMatrixMultiplication(v, matrix, i));
        }
        for (int i = 0; i < t.length; i++) {
            t[i].start();
        }
        try {
            for (int i = 0; i < t.length; i++) {
                t[i].join();
            }
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        long stop = System.nanoTime();
        return stop - start;
    }

    // Times the multithreaded version of dense matrix multiplication.
    public static long timeDense(double [][] A, double [] v) {
        DenseMatrix matrix = new DenseMatrix(A);
        long start = System.nanoTime();
        
        Thread [] threads = new Thread[A.length];
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new DenseMatrixMultiplication(v, matrix, i));
        }
        for(int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            for(int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        long stop = System.nanoTime();
        return stop - start;
    }

    public static long [] timeMatrices(int size, double sparsity) {
        double [][] sparseTable = generateSparseTable(size, sparsity);
        double [][] denseTable = generateDenseTable(size);
        double [] vector = generateVector(size);

        long sparseTime = timeSparse(sparseTable, vector);
        long denseTime = timeDense(denseTable, vector);
        
        return new long[] {sparseTime, denseTime};
    }

   
    public static void main(String[] args){
        int [] sizes = new int [] {3, 5, 10, 25, 50, 100, 500, 1000, 10000, 100000};
        System.out.println("Size, Sparse Time, Dense Time");
        for(int i = 0; i < sizes.length; i++) {
            long [] times = timeMatrices(sizes[i], 0.3);
            System.out.println(sizes[i] + ", " + times[0] + ", " + times[1]);
        }
    }
}
