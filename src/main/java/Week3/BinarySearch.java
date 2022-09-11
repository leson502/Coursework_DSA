package Week3;

import edu.princeton.cs.algs4.StdOut;

public class BinarySearch {
    public static int binarySearch(int[] array, int number) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == number)
                return mid;
            if (array[mid] < number)
                left = mid+1;
            else if (array[mid] > number)
                right = mid-1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 4, 5, 6, 8, 12, 234, 235};
        StdOut.println(binarySearch(array, 234));
    }
}
