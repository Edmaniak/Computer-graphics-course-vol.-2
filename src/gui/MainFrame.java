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
    private JToolBar toolBar;


    public MainFrame(int width, int height) {

        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(App.title);

        JTabbedPane tabs = new JTabbedPane();
        canvas3D = new Canvas3D(new Dimension(width, height), new Color(70, 73, 76));
        canvasCurve = new Canvas(new Dimension(width, height), new Color(70, 73, 76));
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(30, 100));
        controlInit();
        initButtons();

        // Adding everything to the frame
        add(canvas3D, BorderLayout.CENTER);
        add(toolBar, BorderLayout.WEST);


        pack();
        setVisible(true);
    }

    public Canvas getCanvas3D() {
        return canvas3D;
    }

    private void initButtons() {
        ToolButton tetraHedron = new ToolButton("Troj");
        tetraHedron.addActionListener(e ->
                App.app.switchTo(App.app.getSolid("tetrahedron")));
        toolBar.add(tetraHedron);

        ToolButton cube = new ToolButton("Cube");
        cube.addActionListener(e ->
                App.app.switchTo(App.app.getScene().getSolid("cube")));

        toolBar.add(cube);


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


