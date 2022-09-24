import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] rates;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.trials = trials;
        rates = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniformInt(1, n + 1),
                        StdRandom.uniformInt(1, n + 1));
            }
            rates[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(rates);
    }

    public double stddev() {
        return StdStats.stddev(rates);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = 8;
        int trialsNum = 8;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            trialsNum = Integer.parseInt(args[1]);
        }
        PercolationStats test = new PercolationStats(n, trialsNum);

        String result = "mean                    = " + test.mean() + "\n"
                + "stddev                  = " + test.stddev() + "\n"
                + "95% confidence interval = "
                + "[" + test.confidenceLo() + ", " + test.confidenceHi() + "]";
        StdOut.println(result);
    }
}
