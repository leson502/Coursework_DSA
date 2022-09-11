package Week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;

public class ThreeSum {
    public static void main(String[] args) {
        In in = new In("algs4-data/1Kints.txt");
        int[] a = in.readAllInts();
        Integer[] array = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            array[i] = a[i];
        }
        Quick.sort(array);
        int count = 0;
        for (int k = 0; k < array.length; k++) {
            count += TwoSum.twoPointerCount(-array[k], array, k + 1, array.length - 1);
        }
        StdOut.println(count);
    }
}

