import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
    private Node root;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int size;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.size = 1;
            this.rect = rect;
        }
    }

    public KdTree() {  // construct an empty set of point

    }

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) return 0;
        return n.size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, p, 1, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Node insert(Node root, Point2D p, int level, RectHV rect) {
        int cmp = 0;
        if (root == null) {
            return new Node(p, rect);
        }
        if (root.p.equals(p)) return root;
        if (level % 2 != 0)
            cmp = Double.compare(p.x(), root.p.x());
        else
            cmp = Double.compare(p.y(), root.p.y());

        RectHV nextRect = null;
        if (cmp < 0) {
            if (level % 2 != 0)
                nextRect = new RectHV(rect.xmin(), rect.ymin(), root.p.x(), rect.ymax());
            else
                nextRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), root.p.y());

            root.lb = insert(root.lb, p, level + 1, nextRect);
        } else {
            if (level % 2 != 0)
                nextRect = new RectHV(root.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                nextRect = new RectHV(rect.xmin(), root.p.y(), rect.xmax(), rect.ymax());

            root.rt = insert(root.rt, p, level + 1, nextRect);
        }
        root.size = size(root.lb) + size(root.rt) + 1;
        return root;
    }

    // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p, 1);
    }

    private boolean contains(Node root, Point2D p, int level) {
        int cmp = 0;
        if (root == null) return false;
        if (p.equals(root.p)) return true;
        if (level % 2 != 0)
            cmp = Double.compare(p.x(), root.p.x());
        else
            cmp = Double.compare(p.y(), root.p.y());
        if (cmp < 0) {
            return contains(root.lb, p, level + 1);
        } else {
            return contains(root.rt, p, level + 1);
        }
    }

    // does the set contain point p?
    public void draw() {
        drawHelper(root, 1);
    }

    private void drawHelper(Node root, int level) {
        if (root == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(root.p.x(), root.p.y());
        if (level % 2 != 0) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
        }else {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
        }
        drawHelper(root.lb, level + 1);
        drawHelper(root.rt, level + 1);
    }

    // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> cpoints = new ArrayList<>();
        iterHelper(rect, root, cpoints, 1);
        return cpoints;
    }

    private void iterHelper(RectHV rect, Node root, ArrayList<Point2D> cpoints, int level){
        if (root == null) return;
        if (rect.contains(root.p)) cpoints.add(root.p);
        if (level % 2 != 0) {
            if (rect.xmax() < root.p.x()) {
                iterHelper(rect, root.lb, cpoints,level + 1);
            } else {
                if (rect.xmin() < root.p.x()) {
                    iterHelper(rect, root.lb, cpoints, level + 1);
                    iterHelper(rect, root.rt, cpoints, level + 1);
                } else {
                    iterHelper(rect, root.rt, cpoints, level + 1);
                }
            }
        } else {
            if (rect.ymax() < root.p.y()) {
                iterHelper(rect, root.lb, cpoints,level + 1);
            } else {
                if (rect.ymin() < root.p.y()) {
                    iterHelper(rect, root.lb, cpoints, level + 1);
                    iterHelper(rect, root.rt, cpoints, level + 1);
                } else {
                    iterHelper(rect, root.rt, cpoints, level + 1);
                }
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        double dis = Double.MAX_VALUE;
        Point2D nearest = null;
        return nearHelper(p,root,1, nearest, dis);
    }

    private Point2D nearHelper(Point2D p, Node root, int level, Point2D nearest, double min) {
        if (root == null) return nearest;

        if (root.p.distanceSquaredTo(p) <= min) {
            min = root.p.distanceSquaredTo(p);
            nearest = root.p;
        }
        Point2D leftWinner = null;
        Point2D rightWinner = null;
        if (root.lb == null || root.lb.rect.distanceSquaredTo(p) <= min)
            leftWinner = nearHelper(p, root.lb,level + 1, nearest, min);
        if (root.rt == null ||root.rt.rect.distanceSquaredTo(p) <= min)
            rightWinner = nearHelper(p, root.rt,level + 1, nearest, min);
        if (leftWinner != null && rightWinner != null)
            return leftWinner.distanceSquaredTo(p) < rightWinner.distanceSquaredTo(p) ? leftWinner : rightWinner;
        else
            return leftWinner == null ? rightWinner : leftWinner;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.7, 0.2));
        kdtree.insert(new Point2D(0.25, 0.5));
        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
        kdtree.insert(new Point2D(0.9, 0.6));
        System.out.println(kdtree.contains(new Point2D(0.25, 0.5)));
        //kdtree.draw();
        RectHV rect = new RectHV(0, 0, 1, 1);
        for (Point2D p : kdtree.range(rect)) {
            //System.out.println(p);
        }
        Point2D p = new Point2D(0.1, 0.9);
        //System.out.println(kdtree.nearest(p));
    }

}
