import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> lines = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }

        Point[] copyPoints = points.clone();

        Arrays.sort(copyPoints);

        for (int i = 0; i < copyPoints.length - 1; i++) {
            if (copyPoints[i].compareTo(copyPoints[i + 1]) == 0) throw new IllegalArgumentException();
        }

        for (int i = 0; i < copyPoints.length; i++) {
            for (int j = i+1; j < copyPoints.length; j++) {
                for (int k = j+1; k < copyPoints.length; k++) {
                    for (int l = k+1; l < copyPoints.length; l++) {
                        double slope = copyPoints[i].slopeTo(copyPoints[j]);
                        if (slope == copyPoints[i].slopeTo(copyPoints[k]) && slope == copyPoints[i].slopeTo(copyPoints[l])) {
                            lines.add(new LineSegment(copyPoints[i], copyPoints[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lines.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] segments = lines.toArray(new LineSegment[lines.size()]);
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        /*
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(10000, 0));
        points.add(new Point(0, 10000));
        points.add(new Point(3000, 7000));
        points.add(new Point(7000, 3000));
        BruteCollinearPoints fcp = new BruteCollinearPoints(points.toArray(new Point[points.size()]));
        LineSegment[] lines = fcp.segments();
        int lineNumber = fcp.numberOfSegments();
        System.out.println("=================");
        System.out.println("line number: " + lineNumber);
        for (int i = 0; i < lines.length; i++) {
            System.out.println("line " + i + ": " + lines[i].toString());
        }
        */
    }
}