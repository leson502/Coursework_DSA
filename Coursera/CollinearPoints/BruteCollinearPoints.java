import edu.princeton.cs.algs4.Merge;

import java.util.LinkedList;

public class BruteCollinearPoints {
    private int count;
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);
        Point[] newPoints = points.clone();
        Merge.sort(newPoints);
        LinkedList<LineSegment> segmentsList = new LinkedList<>();
        count = 0;
        for (int i = 0; i < newPoints.length; i++) {
            for (int j = i + 1; j < newPoints.length; j++) {
                for (int k = j + 1; k < newPoints.length; k++) {
                    for (int l = k + 1; l < newPoints.length; l++) {
                        if (newPoints[i].slopeTo(newPoints[j]) == newPoints[j].slopeTo(newPoints[k]) &&
                                newPoints[i].slopeTo(newPoints[j]) == newPoints[k].slopeTo(newPoints[l])) {
                            segmentsList.add(new LineSegment(newPoints[i], newPoints[l]));
                            count++;
                        }
                    }
                }
            }
        }
        lineSegments = new LineSegment[count];
        int it = 0;
        for (LineSegment v : segmentsList) {
            lineSegments[it++] = v;
        }
    }

    private void validatePoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}