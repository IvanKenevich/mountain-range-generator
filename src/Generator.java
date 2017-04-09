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
    private Random rand;
    private final int BOUNDS = 1000;

    public Generator() {
        super(null);
        rand = new Random();
        createTimer();
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
    private void simplePointSlopeGenerate() {
        int x = rand.nextInt(BOUNDS);
        int y = rand.nextInt(BOUNDS);

        float slope = rand.nextFloat() + 1;

        xs = new int[]{x - (int) (slope * x), x, x + (int) (slope * x)};
        ys = new int[]{BOUNDS, y, BOUNDS};
    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.setColor(Color.black);

        simplePointSlopeGenerate();
        g.drawPolygon(xs, ys, xs.length);
    }
}
