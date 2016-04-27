import java.awt.BorderLayout;

import javax.swing.JFrame;

public class comp{

    public static void main(String[] args) {

        JFrame frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        single_player pongPanel = new single_player(1);
        frame.add(pongPanel, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}