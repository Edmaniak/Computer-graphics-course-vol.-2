package gui;

import app.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private final Canvas3D canvas3D;
    private final App app = App.app;
    private JToolBar toolBar;


    public MainFrame(int width, int height) {

        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(App.title);
        setFocusable(true);
        requestFocusInWindow();

        canvas3D = new Canvas3D(new Dimension(width, height), new Color(70, 73, 76));
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(40, 100));

        controlInit();
        initButtons();

        // Adding everything to the frame
        add(canvas3D, BorderLayout.CENTER);
        add(toolBar, BorderLayout.WEST);


        pack();
        setVisible(true);
    }

    public Canvas3D getCanvas3D() {
        return canvas3D;
    }

    private void initButtons() {
        ToolButton tetraHedron = new ToolButton("res/tetra.png");
        tetraHedron.addActionListener(e ->
                App.app.switchTo(App.app.getSolid("tetrahedron")));


        ToolButton cube = new ToolButton("res/cube.png");
        cube.addActionListener(e ->
                App.app.switchTo(App.app.getScene().getSolid("cube")));

        ToolButton projection = new ToolButton();
        projection.addActionListener(e -> {
            App.app.getScene().switchProjection();
            App.app.renderScene();
        });
        projection.setText("P/O");

        ToolButton circle = new ToolButton("res/circle.png");
        circle.addActionListener(e -> App.app.switchTo(App.app.getScene().getSolid("circle")));

        ToolButton arc = new ToolButton("res/arc.png");
        arc.addActionListener(e -> App.app.switchTo(App.app.getScene().getSolid("bezier")));

        ToolButton camera = new ToolButton("res/camera.png");
        camera.addActionListener(e -> canvas3D.switchCameraControl());


        toolBar.add(tetraHedron);
        toolBar.add(cube);
        toolBar.add(circle);
        toolBar.add(arc);
        toolBar.add(camera);
        toolBar.add(projection);

    }

    private void controlInit() {

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    App.app.getScene().moveRight();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    App.app.getScene().moveLeft();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    App.app.getScene().moveForward();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    App.app.getScene().moveBackwards();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    App.app.getScene().moveUp();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    App.app.getScene().moveDown();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    App.app.getScene().tiltRight();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    App.app.getScene().tiltLeft();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
                    App.app.getScene().tiltUp();
                    App.app.renderScene();
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    App.app.getScene().tiltDown();
                    App.app.renderScene();
                }
            }
        });


    }


}


