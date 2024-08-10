import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

// import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private int x;
    private int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }                     

 // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) return 1;
        else if (this.y < that.y) return -1;

        if (this.x > that.x) return 1;
        else if (this.x < that.x) return -1;
        throw new IllegalArgumentException();
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        else if (this.y == that.y) {
            return 0;
        }
        return ((double)(that.y - this.y) / (double)(that.x - this.x));
    }

     // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new PointComparatorBySlope();
    }
    
    // TO-BE Removed
    public   void draw() {
        StdDraw.point(x, y);
    }
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
    private class PointComparatorBySlope implements Comparator<Point> {
            public int compare(Point q1, Point q2) {
                double q1Slope = slopeTo(q1);
                double q2Slope = slopeTo(q2);
                int result = 0;
                if (q1Slope > q2Slope) result = 1;
                if (q1Slope < q2Slope) result = -1;
                return result;
            }
         }
    }
 