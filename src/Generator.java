import javax.swing.*;
import java.awt.*;

/**
 * Created by Ivan on 4/2/2017.
 */
public class Generator extends JPanel {

    public Generator() {
        super(null);
    }

    public void paintComponent (Graphics g) {
        g.drawLine(0,1000,500,0);
        g.drawLine(500,0,1000,1000);
    }
}
