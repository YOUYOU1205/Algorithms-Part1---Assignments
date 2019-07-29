import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET() {  // construct an empty set of points
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return points.isEmpty();

    }
    public int size() {
        return points.size();  // number of points in the set
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
       points.add(p);
    }

    // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }

    // does the set contain point p?
    public void draw() {
        for (Point2D p : points) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(p.x(), p.y());
        }
    }

    // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        TreeSet<Point2D> cpoints = new TreeSet<>();
        for (Point2D p : points) {
            if (rect.contains(p)) cpoints.add(p);
        }
        return cpoints;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        double dis = Double.MAX_VALUE;
        Point2D nearest = null ;
        for (Point2D i: points) {
            double temp = p.distanceSquaredTo(i);
            if (temp < dis) {
                dis = temp;
                nearest = i;
            }
        }
        return nearest;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
    // unit testing of the methods (optional)
}
