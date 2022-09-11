package DataStructure.UnionFind;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class UFClient2 {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int[] childCount = new int[N];
        for (int i = 0; i < N; i++)
            childCount[i] = 1;
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        int count = 0;
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                childCount[uf.find(p)] += childCount[uf.find(q)];
                childCount[uf.find(q)] = childCount[uf.find(p)];
                uf.union(p,q);
            }
            count++;
            if (childCount[uf.find(p)] == N) {
                StdOut.println(count);
                return;
            }
        }
        StdOut.println("FAILED");
    }
}


