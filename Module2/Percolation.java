import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] sites;
    private int numberOfRows;
    private int openedSites = 0;
    private WeightedQuickUnionUF system;
    private WeightedQuickUnionUF systemBackWash;
    private int topVirtualSite;
    private int bottomVirtualSite;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        
        sites = new boolean[n][n];
        numberOfRows = n;
        openedSites = 0;
        system = new WeightedQuickUnionUF(n*n + 2);
        systemBackWash = new WeightedQuickUnionUF(n*n + 1);
        topVirtualSite = n * n;
        bottomVirtualSite = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndexes(row, col);
        int newRow = row - 1;
        int newCol = col - 1;
        if (isOpen(row, col)) return;
        openedSites++;
        sites[newRow][newCol] = true;
        int currentNode = rowColTo1Dimention(newRow, newCol);

        if (newRow == 0) {
            system.union(topVirtualSite, currentNode);
            systemBackWash.union(topVirtualSite, currentNode);
        }
        if (newRow == numberOfRows - 1) {
            system.union(bottomVirtualSite, currentNode);
        }
        // Above Node
        if (newRow - 1 >= 0 && isOpen(row - 1, col)) {
            system.union(currentNode, rowColTo1Dimention(newRow-1, newCol));
            systemBackWash.union(currentNode, rowColTo1Dimention(newRow-1, newCol));
        }
        // Below Node
        if (newRow + 1 < numberOfRows && isOpen(row + 1, col)) {
            system.union(currentNode, rowColTo1Dimention(newRow + 1, newCol));
            systemBackWash.union(currentNode, rowColTo1Dimention(newRow + 1, newCol));
        }
        // Left Node
        if (newCol - 1 >= 0 && isOpen(row, col-1)) {
            system.union(currentNode, rowColTo1Dimention(newRow, newCol - 1));
            systemBackWash.union(currentNode, rowColTo1Dimention(newRow, newCol - 1));
        }
        // Right Node
        if (newCol + 1 < numberOfRows && isOpen(row, col + 1)) {
            system.union(currentNode, rowColTo1Dimention(newRow, newCol + 1));
            systemBackWash.union(currentNode, rowColTo1Dimention(newRow, newCol + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndexes(row, col);
        return sites[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndexes(row, col);
        int currentNode = rowColTo1Dimention(row - 1, col - 1);
        return systemBackWash.find(topVirtualSite) == systemBackWash.find(currentNode);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites;
    } 

    // does the system percolate?
    public boolean percolates() {        
        return system.find(topVirtualSite) == system.find(bottomVirtualSite);
    }

    // test client (optional)
    private int rowColTo1Dimention(int row, int col) {
        return row * numberOfRows + col;
    }

    private void validateIndexes(int row, int col) {
        if (row > numberOfRows || row < 1 || col > numberOfRows || col < 1) throw new IllegalArgumentException();
    }
    public static void main(String[] args) {
    }
}