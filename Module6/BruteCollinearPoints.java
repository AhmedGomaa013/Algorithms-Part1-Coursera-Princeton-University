import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] allPoints;
    private ArrayList<LineSegment> segments;
    private int numberOfSegments = 0;
    // finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) {
    validatePoints(points);
    allPoints = points;
    Arrays.sort(allPoints);
    segments = new ArrayList<LineSegment>();
    numberOfSegments = 0;
   }
    // the number of line segments
   public int numberOfSegments() {
    return numberOfSegments;
   }

   // the line segments
   public LineSegment[] segments() {
    int n = allPoints.length;
    if (n < 4) return new LineSegment[1];
    for (int i = 0; i < n - 3; i++) {
        for (int j = i + 1; j < n - 2; j++) {
            for (int k = j + 1; k < n - 1; k++) {
                for (int w = k + 1; w < n; w++) {
                    boolean isSlopeEqual = allPoints[i].slopeTo(allPoints[j]) == allPoints[j].slopeTo(allPoints[k]);
                    isSlopeEqual &= allPoints[j].slopeTo(allPoints[k]) == allPoints[k].slopeTo(allPoints[w]);
                    if (isSlopeEqual) {
                        segments.add(new LineSegment(allPoints[i], allPoints[w]));
                        numberOfSegments++;
                    }
                }
            }
        }
    }
    
    return segments.toArray(new LineSegment[numberOfSegments]);
   }                

   private void validatePoints(Point[] points) {
    if (points == null) throw new IllegalArgumentException();
    for (Point p : points) {
        if (p == null) throw new IllegalArgumentException();
    }
   }
}
