import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] copyPoints;
    private ArrayList<LineSegment> lines = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        this.copyPoints = points.clone();
        if (copyPoints == null) throw new IllegalArgumentException();
        for (Point point : copyPoints) {
            if (point == null) throw new IllegalArgumentException();
        }
        Arrays.sort(copyPoints);
        for (int i = 0; i < copyPoints.length - 1; i++) {
            if (copyPoints[i].compareTo(copyPoints[i + 1]) == 0) throw new IllegalArgumentException();
        }

        for (int i = 0; i < copyPoints.length; i++) {
            //sort,track point and line.
            // basic point: points[i]
            Point basic = copyPoints[i];
            Point[] otherPoints = copyPoints.clone();

            MergeX.sort(otherPoints, basic.slopeOrder());
            int count = 1;
            Point start = otherPoints[1];
            for (int j = 1; j < otherPoints.length - 1; j++) {
                // System.out.println("j: " + basic.slopeTo(otherPoints[j]) + " j + 1: " + basic.slopeTo(otherPoints[j + 1]));
                if (basic.slopeTo(otherPoints[j]) == basic.slopeTo(otherPoints[j + 1])) {
                    count++;
                } else if (count >= 3) {
                    if (basic.compareTo(start) < 0)
                        lines.add(new LineSegment(basic, otherPoints[j]));
                    count = 1;
                    start = otherPoints[j + 1];
                } else {
                    count = 1;
                    start = otherPoints[j + 1];
                }
                if (j == otherPoints.length - 2 && count >= 3) {
                    if (basic.compareTo(start) < 0)
                        lines.add(new LineSegment(basic, otherPoints[j + 1]));
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return lines.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return lines.toArray(new LineSegment[lines.size()]);
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
        points.add(new Point(20000, 21000));
        points.add(new Point(3000, 4000));
        points.add(new Point(14000, 15000));
        points.add(new Point(6000, 7000));

        FastCollinearPoints fcp = new FastCollinearPoints(points.toArray(new Point[points.size()]));
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


