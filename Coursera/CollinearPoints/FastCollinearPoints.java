import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class FastCollinearPoints {
    private int count;
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        Point[] newPoints = points.clone();
        count = 0;
        LinkedList<LineSegment> segmentsList = new LinkedList<>();

        Arrays.sort(newPoints);
        for (int i = 0; i < newPoints.length - 1; i++) {
            Integer[] sortedPos = new Integer[newPoints.length];
            sortedPos[0] = i;

            for (int j = 1; j < sortedPos.length; j++) {
                sortedPos[j] = j;
                if (j <= i)
                    sortedPos[j]--;
            }
            Point temp = newPoints[i];

            Arrays.sort(sortedPos, 1, sortedPos.length,
                    Comparator.comparingDouble(o -> temp.slopeTo(newPoints[o])));

            double currSlope = Double.NEGATIVE_INFINITY;
            int cnt = 0;
            for (int j = 1; j < sortedPos.length; j++) {
                if (temp.slopeTo(newPoints[sortedPos[j]]) == currSlope) {
                    cnt++;
                } else {
                    if (cnt >= 3 && sortedPos[j - cnt] > i) {
                        count++;
                        segmentsList.add(new LineSegment(temp, newPoints[sortedPos[j - 1]]));
                    }
                    cnt = 1;
                    currSlope = temp.slopeTo(newPoints[sortedPos[j]]);
                }
            }
            if (cnt >= 3 && sortedPos[sortedPos.length - cnt] > i) {
                count++;
                segmentsList.add(new LineSegment(temp, newPoints[sortedPos[sortedPos.length - 1]]));
            }
        }
        lineSegments = new LineSegment[count];
        int it = 0;
        for (LineSegment v : segmentsList) {
            lineSegments[it++] = v;
        }
    }

    private void validatePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("src/main/resources/lmao.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}