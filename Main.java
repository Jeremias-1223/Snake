
import Snake2.GamePanelAdapt;
import javax.swing.JFrame;


public class Main {
    //main
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new GamePanelAdapt());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        }

}
