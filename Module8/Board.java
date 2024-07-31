import java.util.Arrays;
import java.util.LinkedList;

public class Board {

    private int[][] board;
    private int hamming;
    private int manhattan;
    private int n;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = copyArrayToNewOne(tiles);
        calculateHamming();
        calculateManhattan();
    }
    
    private int[][] copyArrayToNewOne(int[][] original) {
        int[][] temp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = original[i][j];
            }
        }
        return temp;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    private void calculateHamming() {
        this.hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != calculate1DFromXY(i, j) + 1) hamming++;
            }
        }
    }

    private int calculate1DFromXY(int i, int j) {
        return i * n + j;
    }
    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private void calculateManhattan() {
        manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != calculate1DFromXY(i, j)+1) {
                    int val = board[i][j] -1 ;
                    manhattan += Math.abs(i - (val / n)) + Math.abs(j - (val % n));
                }
            }
        }
    }
    // is this board the goal board?
    public boolean isGoal() {
        int nn = n * n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ((calculate1DFromXY(i, j) + 1) % nn)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		if (that.dimension() != this.dimension()) return false;
        return Arrays.deepEquals(this.board, that.board); 
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> queue = new LinkedList<>();
        int row = 0, col = 0, j = 0;
        for (int i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                     break;
                }
            }
            if (j < n) break;
        }
        if (row == n) row--;
        if (col == n) col--;
        if (row + 1 < n) {
            queue.add(setupBoard(row, col, row+1, col));
        }
        if (row - 1 >= 0) {
            queue.add(setupBoard(row, col, row-1, col));
        }
        
        if (col + 1 < n) {
            queue.add(setupBoard(row, col, row, col+1));
        }
        
        if (col - 1 >= 0) {
            queue.add(setupBoard(row, col, row, col-1));
        }
        
        return queue;
    }

    private Board setupBoard(int i, int j, int newi, int newj) {
        int[][] temp = copyArrayToNewOne(board);
        swap(temp, i, j, newi, newj);
        Board tempBoard = new Board(temp);
        return tempBoard;
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] temp = copyArrayToNewOne(board);
        if (temp[0][0] != 0 && temp[0][1] != 0){
            swap(temp, 0, 0, 0, 1);
        }
        else {
            swap(temp, 1, 0, 1, 1);
        }
        return new Board(temp);
    }

    private void swap(int[][] arr, int x, int y, int xToSwap, int yToSwap) {
        int valToSwap = arr[x][y];
        arr[x][y] = arr[xToSwap][yToSwap];
        arr[xToSwap][yToSwap] = valToSwap;
    }
    // unit testing (not graded)
    public static void main(String[] args) {

    }
}