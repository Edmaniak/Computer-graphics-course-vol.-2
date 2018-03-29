package gui;

import app.App;
import model.objects.SceneObject;
import model.objects.Solid;
import transforms.Point2D;
import transforms.Vec2D;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas3D extends Canvas {

    private Point2D cp;
    private static final double MOUSE_SPEED = 0.05;

    private boolean cameraControl;

    public Canvas3D(Dimension d, Color color) {
        super(d, color);
        controlInit();
        setFocusable(true);
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
                // mouse and camera vector init
                Vec2D delta = new Vec2D(cp.getX() - e.getX(), cp.getY() - e.getY());
                if (!delta.normalized().isPresent())
                    return;
                delta = new Vec2D(delta).normalized().get();
                // Object moving
                if (!cameraControl) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        App.app.getActiveObject().transform.rotate(delta.getY() * MOUSE_SPEED, delta.getX() * MOUSE_SPEED, 0);
                        App.app.renderScene();
                    }
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Vec3D moveVector = new Vec3D(-delta.getX() * MOUSE_SPEED, delta.getY() * MOUSE_SPEED, 0);
                        App.app.getActiveObject().transform.translate(moveVector);
                        App.app.getObjectAxis().transform.translate(moveVector);
                        App.app.renderScene();
                    }
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        App.app.getActiveObject().transform.scale(delta.getY());
                        App.app.renderScene();
                    }
                }
                // camera control
                if (cameraControl) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        App.app.getScene().tilt(delta.mul(MOUSE_SPEED));
                        App.app.renderScene();
                    }
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        App.app.getScene().move(delta);
                        App.app.renderScene();
                    }
                }
            }

        });
    }

    public void switchCameraControl() {
        cameraControl = !cameraControl;
    }

    public void debug(SceneObject sceneObject) {

        // modelovací
        drawString("Model transform matrix >", App.WIDTH - 170, 10);
        drawString(sceneObject.transform.getModel().toString(), App.WIDTH - 125, 30);
        drawString("Model transform object >", 20, 10);
        drawString(sceneObject.transform.toString(), 40, 30);

        // kamera
        drawString("Camera object >", 20, 90);
        drawString(App.app.getScene().getCamera().toString(), 40, 110);

        // Pipeline matrices
        drawString("View matrix >", App.WIDTH - 170, 110);
        drawString(App.app.getScene().getRenderer().getView().toString(), App.WIDTH - 125, 130);

        //autor
        drawString("Adam Ouhrabka PGRF ", App.WIDTH - 150, App.HEIGHT - 50);
    }
}
