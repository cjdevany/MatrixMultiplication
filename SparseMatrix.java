import java.util.*;

// A cell is a single entry in the matrix and contains it's column and value
class Cell {
    int col;
    double value;

    public Cell(int col, double val) {
        this.col = col;
        this.value = val;
    }
}

// A sparsematrix is an array of arraylist<cell>
class SparseMatrix {

    ArrayList<ArrayList<Cell>> matrix;

    public SparseMatrix(int size) {
        this.matrix = new ArrayList<ArrayList<Cell>>();
        for(int i = 0; i < size; i++) {
            this.matrix.add(new ArrayList<Cell>());
        }
    }

    public SparseMatrix(ArrayList<ArrayList<Cell>> matrix) {
        this.matrix = matrix;
    }

    public SparseMatrix(double[][] table){
        this.matrix = new ArrayList<ArrayList<Cell>>();
        for(int i = 0; i < table.length; i++) {
            this.matrix.add(new ArrayList<Cell>());
            for(int j = 0; j < table[i].length; j++) {
                if(table[i][j] == 0) continue;
                else {
                    Cell c = new Cell(j, table[i][j]);
                    this.add(c, i);
                }
            }
        }
    }

    //check the matrix's size
    public int size() {
        return this.matrix.size();
    }

    //adds a cell to a row in the table
    public void add(Cell c, int row) {
        this.matrix.get(row).add(c);
    }

    //returns a double [][] representing this sparse matrix
    public double [][] toTable() {        
        //init table with 0s
        double [][] table = new double[matrix.size()][matrix.size()];
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table.length; j++) {
                table[i][j] = 0;
            }
        }
        
        //scan matrix for values and insert at proper table position
        for(int i = 0; i < this.matrix.size(); i++) {
            for(Cell c : matrix.get(i)) {
                int col = c.col;
                table[i][col] = c.value;
            }
        }

        return table;
    }

    public ArrayList<Cell> getRow(int row) {
        return this.matrix.get(row);
    }

    public void print() {
        double [][] table = this.toTable();
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    //testing sparsematrix
    public static void main(String[] args) {        
        double[][] matrix = {{2, -1, 0, 0}, {-1, 2, -1, 0}, {0, -1, 2, -1}, {0, 0, -1, 2}};
        SparseMatrix m = new SparseMatrix(matrix);
        m.print();
    }
}
