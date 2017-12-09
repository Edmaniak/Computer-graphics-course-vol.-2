package gui;

import app.App;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private final Canvas canvas;
    private final App app = App.app;
    private Point2D cp;
    private static double MOUSE_SPEED = 0.05;


    public MainFrame(int width, int height) {

        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(App.title);

        canvas = new Canvas(new Dimension(width, height), new Color(70, 73, 76));

        controlInit();
        // Adding everything to the frame
        add(canvas, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cp = new Point2D(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Vec2D delta = new Vec2D(cp.getX() - e.getX(), cp.getY() - e.getY()).normalized().get();
                    app.getActiveSolid().transform.rotate(delta.getY()* MOUSE_SPEED,delta.getX()* MOUSE_SPEED,0);
                    app.getSingleAxis().transform.rotate(delta.getY()* MOUSE_SPEED,delta.getX()* MOUSE_SPEED,0);
                    app.renderScene();
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Vec2D delta = new Vec2D(cp.getX() - e.getX(), cp.getY() - e.getY()).normalized().get();
                    app.getActiveSolid().transform.translate(-delta.getX()* MOUSE_SPEED,delta.getY()* MOUSE_SPEED,0);
                    app.getSingleAxis().transform.translate(-delta.getX()* MOUSE_SPEED,delta.getY()* MOUSE_SPEED,0);
                    app.renderScene();
                }

            }

        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
            }
        });

    }

}


