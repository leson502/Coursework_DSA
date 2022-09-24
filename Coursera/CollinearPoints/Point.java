import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException();
        }
        if (this.x - that.x == 0 && this.y - that.y == 0)
            return Double.NEGATIVE_INFINITY;
        else if (this.x - that.x == 0)
            return Double.POSITIVE_INFINITY;
        else if (this.y - that.y == 0)
            return +0.0;
        return ((double) (that.y - this.y)) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (that == null)
            throw new NullPointerException();
        if (this.y == that.y)
            return Integer.compare(this.x, that.x);
        return Integer.compare(this.y, that.y);
    }

    public Comparator<Point> slopeOrder() {
        return (o1, o2) -> {
            if (o1 == null || o2 == null)
                throw new NullPointerException();
            return Double.compare(slopeTo(o1), slopeTo(o2));
        };
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}