import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static class KdNode {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private KdNode left;        // the left/bottom subtree
        private KdNode right;        // the right/top subtree
        private boolean isVertical;

        public KdNode(Point2D point, RectHV rect, boolean isVertical) {
            this.p = point;
            this.rect = rect;
            this.left = null;
            this.right = null;
            this.isVertical = isVertical;
        }
     }
    private int size;
    private KdNode root;
    private RectHV plainDim = new RectHV(0, 0, 1, 1);
    private double minimumDistance = Double.POSITIVE_INFINITY;
    public KdTree() {
        size = 0;
        root = null;
    }
   
    public boolean isEmpty() {
        return size == 0;
    }
   
    public int size() {
        return size;
    }
   
    public void insert(Point2D p) {
        checkNullPoints(p);
        root = insert(root, p, plainDim, true);
    }
    private KdNode insert(KdNode rootNode, Point2D p, RectHV rect, boolean isVertical) {
        if (rootNode == null) {
            this.size++;
            if (isVertical) 
            return new KdNode(p, rect, true);
            else
            return new KdNode(p, rect, false);
        }

        if (p.equals(rootNode.p)) { 
            return rootNode;
        }

        else if (less(rootNode.p, p, isVertical)) {
            RectHV newRect = createNewRect(rect, rootNode.p, isVertical, true);
            rootNode.left = insert(rootNode.left, p, newRect, !isVertical);
        }
        else { 
            RectHV newRect = createNewRect(rect, rootNode.p, isVertical, false);
            rootNode.right = insert(rootNode.right, p, newRect, !isVertical);
        }
        return rootNode;
    }
    private RectHV createNewRect(RectHV rect, Point2D rooPoint2d, boolean isVertical, boolean isLess) {
        RectHV newRect = null;
        if (isVertical && isLess) newRect = new RectHV(rect.xmin(), rect.ymin(), rooPoint2d.x(), rect.ymax());
        else if (isVertical && !isLess) newRect = new RectHV(rooPoint2d.x(), rect.ymin(), rect.xmax(), rect.ymax());
        else if (!isVertical && isLess) newRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), rooPoint2d.y());
        else newRect = new RectHV(rect.xmin(), rooPoint2d.y(), rect.xmax(), rect.ymax());
        return newRect;
    }
    private boolean less(Point2D rootPoint, Point2D pointToAdd, boolean isVertical) {
        if (rootPoint.equals(pointToAdd)) return false;
        if (isVertical) {
            return pointToAdd.x() < rootPoint.x();
        }
        else {
            return pointToAdd.y() < rootPoint.y();
        }
    }
    public boolean contains(Point2D p) {
        checkNullPoints(p);
        return contains(this.root, p) != null;
    }
    private KdNode contains(KdNode rootNode, Point2D point) {
        if (rootNode == null) return null;
        if (point.equals(rootNode.p)) return rootNode;
        else if (less(rootNode.p, point, rootNode.isVertical)) return contains(rootNode.left, point);
        else return contains(rootNode.right, point);
    }
    public void draw() {
        StdDraw.setScale();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        plainDim.draw();
        draw(root);
    }
    private void draw(KdNode node) {
        if (node == null) return;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());
        draw(node.left);
        draw(node.right);
    }
   
    public Iterable<Point2D> range(RectHV rect) {
        checkNullRectangle(rect);
        Queue<Point2D> queue = new Queue<>();
        getRange(queue, this.root, rect);
        return queue;
    }

    private void getRange(Queue<Point2D> queue, KdNode node, RectHV rect) {
        if (node != null) {
            if (rect.intersects(node.rect)) {
                if (rect.contains(node.p)) queue.enqueue(node.p);
            }
            if (node.left != null && rect.intersects(node.left.rect)) getRange(queue, node.left, rect);
            if (node.right != null && rect.intersects(node.right.rect)) getRange(queue, node.right, rect);
        }
    }
   
    public Point2D nearest(Point2D p) {
        checkNullPoints(p);
        if (isEmpty()) return null;
        this.minimumDistance = Double.POSITIVE_INFINITY;
        return getNearest(root, p, null);
    }
    private Point2D getNearest(KdNode node, Point2D p, Point2D nearest) {
        if (node == null) return nearest;
        double distance = p.distanceSquaredTo(node.p);
        if (distance <= this.minimumDistance) {
            this.minimumDistance = distance;
            nearest = node.p;
        }
        nearest = getNearest(node.left, p, nearest);
        nearest = getNearest(node.right, p, nearest);
        return nearest;
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
