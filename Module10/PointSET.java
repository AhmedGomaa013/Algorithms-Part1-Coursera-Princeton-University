import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> tree = new SET<>();
    public PointSET() {
    }
    public boolean isEmpty() {
        return tree.isEmpty();
    }
    public int size() {
        return tree.size();
    }
    public void insert(Point2D p) {
        checkNullPoints(p);
        tree.add(p);
    }
    public boolean contains(Point2D p) {
        checkNullPoints(p);
        return tree.contains(p);
    }
    public void draw() {
        for (var p : tree) {
            StdDraw.point(p.x(), p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        checkNullRectangle(rect);
        Queue<Point2D> queue = new Queue<>();
        for (var p : tree) {
            if (rect.contains(p)) queue.enqueue(p);
        }
        return queue;
    }
    public Point2D nearest(Point2D p) {
        checkNullPoints(p);
        double distance = Double.POSITIVE_INFINITY;
        Point2D pointToreturn = null;
        for (var point : tree) {
            var temp = point.distanceSquaredTo(p);
            if (temp < distance) {
                pointToreturn = point;
                distance = temp;
            }
        }
        return pointToreturn;
    }

    private void checkNullPoints(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
    }
    private void checkNullRectangle(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
    }
    public static void main(String[] args) {
        
    }
 }