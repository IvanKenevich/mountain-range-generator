import javax.swing.*;

/**
 * Created by Ivan on 4/2/2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mountain Range");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020,1020);
        frame.add(new Generator());
        frame.setVisible(true);
    }
}
