import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private int[][] tiles;
    private int grid;
    private int emptyX;
    private int emptyY;
    private int mht;
    private int hmg;

    public Board(int[][] tiles) {
        validateTiles(tiles);
        this.tiles = tiles;
        grid = tiles.length;
        hmg = 0;
        mht = 0;
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                if (tiles[i][j] == 0) {
                    emptyX = i;
                    emptyY = j;
                } else {
                    if (tiles[i][j] != goalTiles(i, j))
                        hmg++;
                    mht += distanceGoal(tiles[i][j], i, j);
                }

            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(grid);
        for (int i = 0; i < grid; i++) {
            result.append("\n");
            for (int j = 0; j < grid; j++) {
                result.append(" ").append(String.valueOf(tiles[i][j])).append(" ");
            }
        }
        return result.toString();
    }

    public int dimension() {
        return grid;
    }

    public int hamming() {
        return hmg;
    }

    public int manhattan() {
        return mht;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Board)) return false;
        Board that = (Board) obj;
        if (that.grid != this.grid)
            return false;
        return Arrays.deepEquals(that.tiles, this.tiles);
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        if (emptyX == 0 && emptyY <= 1)
            return new Board(swapTiles(grid - 1, grid - 1, grid - 1, grid - 2));
        else
            return new Board(swapTiles(0, 0, 0, 1));
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> list = new LinkedList<>();

        if (validateCoordinate(emptyX + 1, emptyY))
            list.add(new Board(swapTiles(emptyX, emptyY, emptyX + 1, emptyY)));
        if (validateCoordinate(emptyX - 1, emptyY))
            list.add(new Board(swapTiles(emptyX, emptyY, emptyX - 1, emptyY)));
        if (validateCoordinate(emptyX, emptyY + 1))
            list.add(new Board(swapTiles(emptyX, emptyY, emptyX, emptyY + 1)));
        if (validateCoordinate(emptyX, emptyY - 1))
            list.add(new Board(swapTiles(emptyX, emptyY, emptyX, emptyY - 1)));
        return list;
    }

    private void validateTiles(int[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException();
        boolean[] isContain = new boolean[matrix.length * matrix.length];
        for (int[] row : matrix) {
            if (row == null || row.length != matrix.length)
                throw new IllegalArgumentException();

            for (int e : row) {
                if (e >= matrix.length * matrix.length || e < 0)
                    throw new IllegalArgumentException();
                isContain[e] = true;
            }
        }

        for (boolean v : isContain) {
            if (!v)
                throw new IllegalArgumentException();
        }
    }

    private boolean validateCoordinate(int x, int y) {
        return (0 <= x && x < grid && 0 <= y && y < grid);
    }


    private int[][] cloneTiles() {
        int[][] copyTiles = new int[grid][grid];
        for (int i = 0; i < grid; i++) {
            System.arraycopy(tiles[i], 0, copyTiles[i], 0, grid);
        }
        return copyTiles;
    }

    private int[][] swapTiles(int x1, int y1, int x2, int y2) {
        int[][] swaped = cloneTiles();
        int temp = swaped[x1][y1];
        swaped[x1][y1] = swaped[x2][y2];
        swaped[x2][y2] = temp;
        return swaped;
    }

    private int goalTiles(int row, int col) {
        if (row < 0 || row >= grid || col < 0 || col >= grid)
            throw new IllegalArgumentException();
        return (row * grid + col + 1) % (grid * grid);
    }

    private int distanceGoal(int index, int row, int col) {
        validateCoordinate(row, col);
        if (index == 0)
            index = grid * grid - 1;
        else
            index--;
        return Math.abs(row - (index / grid)) + Math.abs(col - (index % grid));
    }

    public static void main(String[] args) {
        int[][] tiles = {{1, 2, 3},
                {0, 7, 6},
                {5, 4, 8}};
        Board board = new Board(tiles);

        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
    }
}
