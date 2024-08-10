import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] results;
    private int numOfTrials = 0;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.results = new double[trials];
        numOfTrials = trials;
        for (int i = 0; i < trials; i++) {
            var perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = (int) StdRandom.uniformLong((long) n);
                int col = (int) StdRandom.uniformLong((long) n);
                perc.open(row + 1, col + 1);
            }
            this.results[i] = (double) perc.numberOfOpenSites() / (double) (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(numOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(numOfTrials));
    }

   // test client (see below)
   public static void main(String[] args) {
    var test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    StdOut.println("mean\t = "+ test.mean());
    StdOut.println("stddev\t = "+ test.stddev());
    StdOut.println("95% confidence interval = ["+ test.confidenceLo()+", " + test.confidenceHi() + "]");
   }
}
