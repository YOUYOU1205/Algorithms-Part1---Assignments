import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int OPEN = 1;
	
	private WeightedQuickUnionUF uf;
	private int[][] grid;
	private int length;
	private int openSite;
	private int top;
	private int bottom;
	
	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new IllegalArgumentException("n could not be negative");
		uf = new WeightedQuickUnionUF(n * n + 2);
		grid = new int[n+1][n+1];
		length = n;
		openSite = 0;
		top =  n * n;
		bottom = n * n + 1;
	}
	
	public void open(int row, int col) {
		// open site (row, col) if it is not open already

		if (!isOpen(row, col)) {
		    grid[row][col] = OPEN;
		    openSite++;
		    if (col < length && isOpen(row, col + 1)) {
		    	uf.union(length * (row - 1) + col, length * (row - 1) + col - 1);
		    }
		    if (row > 1 && isOpen(row - 1, col)) {
		    	uf.union(length * (row - 2) + col - 1, length * (row - 1) + col - 1);
		    }
		    if (row < length && isOpen(row + 1, col)) {
		    	uf.union(length * row + col - 1, length * (row - 1) + col - 1);
		    }
		    if (col > 1 && isOpen(row, col - 1)) {
		    	uf.union(length * (row - 1) + col - 2, length * (row - 1) + col - 1);
		    }
		    
		    if (row == 1) {
				uf.union(top, col - 1);
			}
			if (row == length) {
				uf.union(bottom, length * (row - 1) + col - 1);
			}
		}
		return;
	}
	
	public boolean isOpen(int row, int col) {
		// is site (row, col) open? 
		
		if (!inBound(row, col))
			throw new IllegalArgumentException();
		return grid[row][col] == OPEN;
	}
	   
	public boolean isFull(int row, int col) {
		// is site (row, col) full?   
		
		if (!inBound(row, col))
			throw new IllegalArgumentException();
		return uf.connected(top, length * (row - 1) + col - 1);
	}
	   
	public int numberOfOpenSites() {
		// number of open sites
		return openSite;
	}
	
	public boolean percolates() {
		// does the system percolate?
		return uf.connected(top, bottom);
	}
	
	private boolean inBound(int row, int col) {
		return row >= 1 && col >= 1 && row <= length && col <= length;
	}
	
	public static void main(String[] args) {
		Percolation p = new Percolation(1);
		System.out.println(p.isOpen(1, 1));
		p.open(1, 1);
		System.out.println(p.isOpen(1, 1));
		System.out.println(p.isFull(1, 1));
		System.out.println(p.percolates());
		
	}
}
