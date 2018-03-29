package gui;

import app.App;
import com.sun.deploy.panel.ExceptionListDialog;
import renderer.Renderer;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private final Canvas3D canvas3D;
    private final JToolBar toolBar;
    private final App app;
    private final JPanel panel;


    public MainFrame(int width, int height, App app) {

        this.app = app;

        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(App.title);
        setFocusable(true);
        requestFocusInWindow();

        canvas3D = new Canvas3D(new Dimension(width, height), new Color(70, 73, 76));
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(30, 100));
        panel = new JPanel();

        controlInit();
        initButtons();
        initSliders();

        // Adding everything to the frame
        add(canvas3D, BorderLayout.CENTER);
        add(toolBar, BorderLayout.WEST);
        add(panel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void initSliders() {

        JSlider ambientIntensity = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
        ambientIntensity.addChangeListener(e -> {
            app.getScene().getAmbientLight().setIntensity((double) ambientIntensity.getValue() / 100);
            app.renderScene();
        });

        JLabel ambientLabel = new JLabel("Ambient intensity");

        panel.add(ambientLabel);
        panel.add(ambientIntensity);

    }

    public Canvas3D getCanvas3D() {
        return canvas3D;
    }

    private void initButtons() {

        ToolButton cube = new ToolButton("res/cube.png");
        cube.addActionListener(e -> app.switchTo("cube"));

        ToolButton camera = new ToolButton("res/camera.png");
        camera.addActionListener(e -> canvas3D.switchCameraControl());

        ToolButton projection = new ToolButton("res/per.png");
        projection.addActionListener(e -> {
            app.getScene().switchProjection();
            app.renderScene();
        });

        ToolButton wire = new ToolButton("res/wire.png");
        wire.addActionListener(e -> {
            app.getScene().getRenderer().switchWire();
            app.renderScene();
        });

        ToolButton wireFull = new ToolButton("res/wire-color.png");
        wireFull.addActionListener(e -> {
            app.getScene().getRenderer().swichtWireFull();
            app.renderScene();
        });

        JButton control = new JButton("OVLÁDÁNÍ");
        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "poznamka.txt");
        control.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pb.start();
                } catch (Exception ex) {

                }
            }
        });

        toolBar.add(cube);
        toolBar.add(camera);
        toolBar.add(projection);
        toolBar.add(wire);
        toolBar.add(wireFull);

        panel.add(control);

    }

    private void controlInit() {

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D)
                    App.app.getScene().moveRight();

                if (e.getKeyCode() == KeyEvent.VK_A)
                    App.app.getScene().moveLeft();

                if (e.getKeyCode() == KeyEvent.VK_W)
                    App.app.getScene().moveForward();

                if (e.getKeyCode() == KeyEvent.VK_S)
                    App.app.getScene().moveBackwards();

                if (e.getKeyCode() == KeyEvent.VK_R)
                    App.app.getScene().moveUp();

                if (e.getKeyCode() == KeyEvent.VK_F)
                    App.app.getScene().moveDown();

                if (e.getKeyCode() == KeyEvent.VK_E)
                    App.app.getScene().tiltRight();

                if (e.getKeyCode() == KeyEvent.VK_Q)
                    App.app.getScene().tiltLeft();

                if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
                    App.app.getScene().tiltUp();

                if (e.getKeyCode() == KeyEvent.VK_SHIFT)
                    App.app.getScene().tiltDown();

                if (e.getKeyCode() == KeyEvent.VK_J)
                    App.app.getScene().getLights().get(0).transform.translate(new Vec3D(-0.1, 0, 0));

                App.app.renderScene();
            }
        });
    }
}


