import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.*;

public class PercolationStats {
	private double[] results;
	private int trials;
	private final double c = 1.96;
	public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
	     if (n <= 0 || trials <= 0) {
	    	 throw new java.lang.IllegalArgumentException("sorry wrong input for trials or too ugly");
	     }
	     results = new double[trials];
	     this.trials = trials;
	     for (int i = 0; i < trials; i++) {
	    	 results[i] = doSimulation(n);
	     }
	}
	
	private double doSimulation(int n) {
		Percolation p = new Percolation(n);
        
        while (!p.percolates()) {
        	 int row = StdRandom.uniform(1, n + 1);
             int col = StdRandom.uniform(1, n + 1);
             p.open(row, col);
        
        }
		return p.numberOfOpenSites()/ (n * n * 1.0);
	}

	public double mean() {                         // sample mean of percolation threshold
		return StdStats.mean(results);
    }
	
    public double stddev() {               // sample standard deviation of percolation threshold
    	if (trials == 1)
    		return Double.NaN;
	    return StdStats.stddev(results);
    }
    
    public double confidenceLo() {               // low  endpoint of 95% confidence interval
	    return mean() - c * stddev() / Math.sqrt((double) trials);
    }
    
    public double confidenceHi()  {                // high endpoint of 95% confidence interval
    	return mean() + c * stddev() / Math.sqrt((double) trials);
    }

    public static void main(String[] args)  {      // test client (described below)
    	int n = 50;

    	Stack<Integer> stack = new Stack<Integer>();
    	while (n > 0) {
    	    stack.push(n % 2);
    	    n = n / 2;
    	}

    	for (int digit : stack) {
    	    StdOut.print(digit);
    	}

    	StdOut.println();
    	
    	
    	//PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        //String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        //StdOut.println("mean                    = " + ps.mean());
        //StdOut.println("stddev                  = " + ps.stddev());
        //StdOut.println("95% confidence interval = " + confidence);
   }

}
