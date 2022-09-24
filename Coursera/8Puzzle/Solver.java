import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private boolean isSlove;
    private List<Board> solutions;

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        isSlove = false;
        MinPQ<Node> pq = new MinPQ<>();

        pq.insert(new Node(initial, null));
        pq.insert(new Node(initial.twin(), null));

        while (!pq.min().board.isGoal()) {
            Node node = pq.delMin();
            for (Board board : node.board.neighbors()) {
                if (node.parent != null && node.parent.board.equals(board))
                    continue;
                pq.insert(new Node(board, node));
            }
        }

        Node it = pq.min();
        solutions = new ArrayList<>();
       Stack<Board> trace = new Stack<>();
        while (it != null) {
            trace.push(it.board);
            it = it.parent;
        }

        while (!trace.isEmpty())
            solutions.add(trace.pop());

        isSlove = solutions.get(0).equals(initial);
    }

    public boolean isSolvable() {
        return isSlove;
    }

    public int moves() {
        if (isSolvable()) return solutions.size() - 1;
        return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) return solutions;
        return null;
    }

    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node parent;
        private int moves;
        private int manhattan;

        public Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
            manhattan = board.manhattan();
            if (parent == null)
                moves = 0;
            else
                moves = parent.moves + 1;
        }

        private int heuristics() {
            return (moves + manhattan);
        }

        @Override
        public int compareTo(Node other) {
            int distance = this.heuristics() - other.heuristics();
            return Integer.compare(distance, 0);
        }
    }

    public static void main(String[] args) {
        int[][] tiles =
                {{2, 3, 4, 8},
                        {1, 6, 0, 12},
                        {5, 10, 7, 11},
                        {9, 13, 14, 15}};
        Solver solver = new Solver(new Board(tiles));
        for (Board lmao :
                solver.solution()) {
            StdOut.println(lmao);
        }

        StdOut.println(solver.moves());
    }
}
