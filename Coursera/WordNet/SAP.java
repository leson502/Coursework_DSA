import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph graph;

    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        graph = new Digraph(G);
    }

    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(graph, w);

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i))
                min = Math.min(bfs1.distTo(i) + bfs2.distTo(i), min);
        }
        return (min == Integer.MAX_VALUE) ? -1 : min;
    }

    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(graph, w);

        int min = Integer.MAX_VALUE;
        int ancest = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i) && min > bfs1.distTo(i) + bfs2.distTo(i)) {
                min = bfs1.distTo(i) + bfs2.distTo(i);
                ancest = i;
            }
        }

        return ancest;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return -1;

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(graph, w);

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i))
                min = Math.min(bfs1.distTo(i) + bfs2.distTo(i), min);
        }
        return (min == Integer.MAX_VALUE) ? -1 : min;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return -1;

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(graph, w);

        int min = Integer.MAX_VALUE;
        int ancest = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i) && min > bfs1.distTo(i) + bfs2.distTo(i)) {
                min = bfs1.distTo(i) + bfs2.distTo(i);
                ancest = i;
            }
        }

        return ancest;
    }

    private void validateVertex(Iterable<Integer> v) {
        if (v == null)
            throw new IllegalArgumentException();
        for (Integer i : v) {
            if (i == null)
                throw new IllegalArgumentException();
            validateVertex(i);
        }

    }

    private void validateVertex(int v) {
        if (v < 0 || v >= graph.V())
            throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        In in = new In("src/main/resources/input.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
