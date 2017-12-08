package gui;

import app.App;
import javafx.scene.input.KeyCode;
import model.objects.Axis;
import model.objects.Cube;
import model.objects.Solid;
import model.objects.TetraHedron;
import renderer.RasterizerLine;
import renderer.RasterizerTriangle;
import renderer.Renderer;
import renderer.Scene;
import transforms.Mat4PerspRH;
import transforms.Mat4RotY;
import transforms.Mat4ViewRH;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    private final Canvas canvas;
    private final Dimension dimension;

    public MainFrame(int width, int height) {

        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(App.title);

        dimension = new Dimension(width, height);

        // app reference

        // Canvas
        canvas = new Canvas(dimension);


/*
        RasterizerLine rl = new RasterizerLine(canvas.getMainBuffer());
        RasterizerTriangle rt = new RasterizerTriangle(canvas.getMainBuffer());

        renderer = new Renderer(rl, rt);
        renderer.setModel(new Mat4RotY(Math.PI/2.5));
        renderer.setProjection(new Mat4PerspRH(Math.PI / 4, 1, 0.001, 100));
        renderer.setView(new Mat4ViewRH(new Vec3D(0, 2, 8), new Vec3D(0, -0.05, -1), new Vec3D(0, 1, 0)));

        Solid s = new Cube(Color.WHITE);
        renderer.render(s);
        System.out.println("-------------");
*/
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    /*canvas.clear(Color.BLACK);
                    renderer.setModel(new Mat4RotY(Math.PI));
                    renderer.render(s);*/

                }
            }
        });

        // Adding everything to the frame
        add(canvas, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}


