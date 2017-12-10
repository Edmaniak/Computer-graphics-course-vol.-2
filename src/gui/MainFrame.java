package gui;

import app.App;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private final Canvas3D canvas3D;
    private final Canvas canvasCurve;
    private final App app = App.app;
    private Point2D cp;
    private static double MOUSE_SPEED = 0.05;


    public MainFrame(int width, int height) {

        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(App.title);

        JTabbedPane tabs = new JTabbedPane();
        canvas3D = new Canvas3D(new Dimension(width, height), new Color(70, 73, 76));
        canvasCurve = new Canvas(new Dimension(width, height), new Color(70, 73, 76));

        tabs.addTab("3D", canvas3D);
        tabs.addTab("Curves", canvasCurve);

        controlInit();

        // Adding everything to the frame
        add(tabs, BorderLayout.CENTER);


        pack();
        setVisible(true);
    }

    public Canvas getCanvas3D() {
        return canvas3D;
    }

    private void controlInit() {

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    System.out.println("Camera right");
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("Camera left");
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    System.out.println("Camera up");
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    System.out.println("Camera down");
                }
            }
        });



    }

}


