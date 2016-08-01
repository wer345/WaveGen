package learning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Test {

    public static void main(String[] args) {
        final Test test = new Test();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                test.createAndShowGUI();
            }
        });
    }

    protected void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setTitle("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(":MOUSE_RELEASED_EVENT:");
            }
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("----------------------------------\n:MOUSE_PRESSED_EVENT:");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println(":MOUSE_EXITED_EVENT:");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println(":MOUSE_ENTER_EVENT:");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(":MOUSE_CLICK_EVENT:");
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

}