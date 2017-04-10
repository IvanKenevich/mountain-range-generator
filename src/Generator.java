import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Generator class that generates mountains and draws them.
 */
public class Generator extends JPanel implements ActionListener {

    private int[] xs, ys;
    Point[] points;
    float[] slopes;
    private Random rand;
    private final int BOUNDS = 1000;
    private final int XBOUNDS = 1900;
    private final int YBOUNDS = 1000;
    private final int NUM_MOUNTAINS = 6;
    private final int MINIMUM_SEPARATION = 200;

    public Generator() {
        super(null);
        rand = new Random();

        points = new Point[NUM_MOUNTAINS];
        slopes = new float[NUM_MOUNTAINS];

        xs = new int[points.length * 3];
        ys = new int[points.length * 3];

        advancedPointSlopeGenerate();
        //repaint();
        //createTimer();
    }

    private void createTimer() {
        Timer t = new Timer(1000, this);
        t.start();
    }

    /*
    Just for testing!
    Generates mountains continuously - needs to be changed in the future
     */
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /*
    Generates asymmetric, overlapping, ugly mountains
     */
    private void simpleGenerate() {
        int x1 = rand.nextInt(BOUNDS);
        int x2 = rand.nextInt(BOUNDS - x1) + x1;
        int x3 = rand.nextInt(x2 - x1) + x1;
        xs = new int[]{x1, x2, x3};

        int y1 = BOUNDS;
        int y2 = BOUNDS; // redundant but meaningful
        int y3 = rand.nextInt(BOUNDS);
        ys = new int[]{y1, y2, y3};
    }

    /*
    Generates symmetric, overlapping, ugly mountains
     */
    private void simplePointSlopeGenerate(Graphics g) {
        int x = rand.nextInt(BOUNDS);
        int y = rand.nextInt(BOUNDS);

        float slope = rand.nextFloat();

        xs = new int[]{x - (int) ((BOUNDS - y) / slope), x, x + (int) ((BOUNDS - y) / slope)};
        ys = new int[]{BOUNDS, y, BOUNDS};

        g.drawPolygon(xs, ys, xs.length);
    }

    private void advancedPointSlopeGenerate() {
        int x = XBOUNDS / 2;
        int y = 0;
        float slope = rand.nextFloat() + 2.0f;
        points[0] = new Point(x, y);
        slopes[0] = slope;

        System.out.printf("I made the initial point with x: %d, y: %d, slope: %f\n", x, y, slope);

        // creating new points
        for (int i = 1; i < points.length; i++) {
            // make a new point
            y = rand.nextInt(YBOUNDS);
            x = rand.nextInt(XBOUNDS);
            System.out.printf("I made and going going to test point number %d with x: %d, y: %d\n", i, x, y);
            // it is safe to assume that this point doesn't follow the rules
            boolean acceptable = false;
            while (!acceptable) {
                System.out.println("while loop");
                System.out.printf("%d points to be checked exist\n", i);
                // iterating over existing points
                int numFailures = 0;
                for (int j = 0; j < i; j++) {
                    // if a new potential point is within another mountain
                    if (points[j].x - bound(points[j].y, y, slopes[j]) < x &&
                            points[j].x + bound(points[j].y, y, slopes[j]) > x) {
                        System.out.printf("At y: %d, x: %d is greater than %d and smaller than %d which makes it unacceptable\n",
                                y, x, points[j].x - bound(points[j].y, y, slopes[j]), points[j].x + bound(points[j].y, y, slopes[j]));

                        numFailures++;
                        // if you went through all the mountains and still didn't find a spot
                        // then this layer is fully occupied and you need to try a higher y-coordinate
                        if (numFailures == i-1) {
                            y = rand.nextInt(y);
                            break;
                        }

                        x = rand.nextInt(XBOUNDS);
                    }
                    // if you found no overlap
                    else {
                        // and went through all the mountains
                        if (j == i - 1) {
                            acceptable = true; // then this coordinate pair is good and can make a new mountain
                            break;
                        }
                    }
                }
            }
            points[i] = new Point(x, y);
            slopes[i] = rand.nextFloat() + 2.0f;
            System.out.printf("Finalized point with x: %d, y: %d, and gave it slope: %f\n", x, y, slopes[i]);
        }

        for (int i = 0; i < points.length; i++) {
            System.out.printf("Point number %d\n", i + 1);
            xs[i*3] = points[i].x - (int) ((BOUNDS - points[i].y) / slopes[i]);
            xs[i*3 + 1] = points[i].x;
            xs[i*3 + 2] = points[i].x + (int) ((BOUNDS - points[i].y) / slopes[i]);
            System.out.printf("xs: %d, %d, %d\n", xs[i], xs[i + 1], xs[i + 2]);
            ys[i*3] = YBOUNDS;
            ys[i*3 + 1] = points[i].y;
            ys[i*3 + 2] = YBOUNDS;
            System.out.printf("ys: %d, %d, %d\n", ys[i], ys[i + 1], ys[i + 2]);
            System.out.printf("slope %f\n",slopes[i]);
        }

    }

    private int bound(int pointY, int potentialY, float slope) {
        return (int) ((potentialY - pointY) / slope) + MINIMUM_SEPARATION;
    }


    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        for (int i = 0; i<points.length; i++) {
            g.setColor(Color.black);
            g.drawString("P"+(i+1),xs[i*3+1],ys[i*3+1]);
            g.drawPolygon(new int[]{xs[i*3],xs[i*3+1],xs[i*3+2]}, new int[]{ys[i*3],ys[i*3+1],ys[i*3+2]}, 3);
            g.setColor(Color.white);
            g.fillPolygon(new int[]{xs[i*3]+1,xs[i*3+1],xs[i*3+2]-1}, new int[]{ys[i*3],ys[i*3+1]+1,ys[i*3+2]}, 3);
        }
    }
}
