import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] allPoints;
    private Point[] temp;
    private ArrayList<LineSegment> segments;
    private int numOfSegments = 0;
    private int n = 0;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        n = points.length;
        allPoints = Arrays.copyOf(points, n);
        temp = Arrays.copyOf(points, n);
        Arrays.sort(temp);
        numOfSegments = 0;
        segments = new ArrayList<LineSegment>();
    }

    public int numberOfSegments() {
        return numOfSegments;
    }
    public LineSegment[] segments() {
        if (n < 4) return new LineSegment[0];

        for (int i = 0; i < n; i++) {
            Arrays.sort(allPoints, temp[i].slopeOrder());
            checkCollinearPoints(temp[i]);
        }
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }

    private void validatePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
    }

    private void checkCollinearPoints(Point p) {
        int count = 1, i = 2;
        int start = 1;
        for (; i < n; i++) {
            if (p.slopeTo(allPoints[start]) == p.slopeTo(allPoints[i])) {
                if (count == 1) count += 2;
                else count++;
            }
            else {
                if (count >= 4) {
                    Arrays.sort(allPoints, start, i);
                    if (allPoints[start].compareTo(p) > 0) {
                        segments.add(new LineSegment(p, allPoints[i-1]));
                        numOfSegments++;
                    }
                }
                count = 1;
                start = i;
            }
        }
        if (count >= 4) {
            Arrays.sort(allPoints, start, i);
            if (allPoints[start].compareTo(p) > 0) {
                segments.add(new LineSegment(p, allPoints[i-1]));
                numOfSegments++;
            }
        }
    }
}
