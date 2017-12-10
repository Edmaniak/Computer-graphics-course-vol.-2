package gui;

import app.App;
import transforms.Point2D;
import transforms.Vec2D;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Optional;

public class Canvas3D extends Canvas {

    private Point2D cp;
    private static double MOUSE_SPEED = 0.05;

    public Canvas3D(Dimension d, Color color) {
        super(d, color);
        controlInit();
    }

    private void controlInit() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cp = new Point2D(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Vec2D delta = new Vec2D(cp.getX() - e.getX(), cp.getY() - e.getY());
                if (!delta.normalized().isPresent())
                    return;
                delta = new Vec2D(delta).normalized().get();
                if (SwingUtilities.isRightMouseButton(e)) {
                    App.app.getActiveSolid().transform.rotate(delta.getY() * MOUSE_SPEED, delta.getX() * MOUSE_SPEED, 0);
                    App.app.getSingleAxis().transform.rotate(delta.getY() * MOUSE_SPEED, delta.getX() * MOUSE_SPEED, 0);
                    App.app.renderScene();
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    App.app.getActiveSolid().transform.translate(-delta.getX() * MOUSE_SPEED, delta.getY() * MOUSE_SPEED, 0);
                    App.app.getSingleAxis().transform.translate(-delta.getX() * MOUSE_SPEED, delta.getY() * MOUSE_SPEED, 0);
                    App.app.renderScene();
                }
                if(SwingUtilities.isMiddleMouseButton(e)) {
                    System.out.println(delta.getY());
                    App.app.getActiveSolid().transform.scale(delta.getY());
                    App.app.renderScene();
                }

            }

        });

    }

}
