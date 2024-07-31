import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {
        Point p, q;
        public LineSegment(Point p, Point q) {
            this.p = p;
            this.q = q;
        }
        
        // string representation
        public String toString() {
            return String.format("(%d, %d) -> (%d, %d)", p.x, p.y, q.x,q.y);
        }

        public void draw() {
            StdDraw.line(p.x, p.y, q.x, q.y);
        }
     }
