package Week2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;


public class TwoSum {
    public static int twoPointerCount(int sum, Integer[] array, int begin, int end) {
        int count = 0;
        int i = begin;
        int j = end;
        while (i < j) {
            if (array[i] + array[j] > sum)
                j--;
            else if (array[i] + array[j] < sum)
                i++;
            else if (array[i] + array[j] == sum) {
                count++;
                if (array[i].equals(array[i + 1])) {
                    i++;
                } else if (array[j].equals(array[j - 1])) {
                    j--;
                } else {
                    i++;
                    j--;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        In in = new In("algs4-data/1Kints.txt");
        int[] a = in.readAllInts();
        Integer[] array = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            array[i] = a[i];
        }
        Quick.sort(array);
        StdOut.println(twoPointerCount(0, array, 0, array.length - 1));
    }
}
