import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] matrix;
    private final int size;
    private final int topNode;
    private final int botNode;

    private int openSites;
    private final WeightedQuickUnionUF connectUF;
    private final WeightedQuickUnionUF fullUF;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        size = n;
        openSites = 0;
        topNode = n * n;
        botNode = n * n + 1;
        matrix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                matrix[i][j] = false;
        }
        connectUF = new WeightedQuickUnionUF(n * n + 2);
        fullUF = new WeightedQuickUnionUF(n * n + 2);
    }

    private boolean invalid(int row, int col) {
        return (0 < row && row <= size && 0 < col && col <= size);
    }

    private int getId(int row, int col) {
        return (row - 1) * size + col - 1;
    }
    
    public void open(int row, int col) {
        if (!invalid(row, col))
            throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;
        matrix[row - 1][col - 1] = true;
        openSites++;
        if (row == 1) {
            connectUF.union(getId(row, col), topNode);
            fullUF.union(getId(row, col), topNode);
        }
        if (row == size) {
            connectUF.union(getId(row, col), botNode);
        }

        if (invalid(row, col + 1) && isOpen(row, col + 1)) {
            connectUF.union(getId(row, col), getId(row, col + 1));
            fullUF.union(getId(row, col), getId(row, col + 1));
        }

        if (invalid(row, col - 1) && isOpen(row, col - 1)) {
            connectUF.union(getId(row, col), getId(row, col - 1));
            fullUF.union(getId(row, col), getId(row, col - 1));
        }

        if (invalid(row + 1, col) && isOpen(row + 1, col)) {
            connectUF.union(getId(row, col), getId(row + 1, col));
            fullUF.union(getId(row, col), getId(row + 1, col));
        }

        if (invalid(row - 1, col) && isOpen(row - 1, col)) {
            connectUF.union(getId(row, col), getId(row - 1, col));
            fullUF.union(getId(row, col), getId(row - 1, col));
        }
    }

    public boolean isOpen(int row, int col) {
        if (!invalid(row, col)) {
            throw new IllegalArgumentException();
        }
        return matrix[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (!invalid(row, col)) {
            throw new IllegalArgumentException();
        }
        return fullUF.find(getId(row, col)) == fullUF.find(topNode);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return connectUF.find(topNode) == connectUF.find(botNode);
    }
}
