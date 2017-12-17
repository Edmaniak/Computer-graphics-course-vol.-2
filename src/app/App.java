package app;

import gui.MainFrame;
import model.objects.BezierCurve;
import model.objects.*;
import model.Scene;
import transforms.Point3D;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;

public class App {

    public static final String title = "3D TABLE";
    public static final Color ACTIVE_COLOR = Color.RED;
    public static final Color IDLE_COLOR = Color.WHITE;
    public static MainFrame gui;
    public static App app;
    public static final int WIDTH = 1055;
    public static final int HEIGHT = 800;
    public static final int RATIO = WIDTH / HEIGHT;
    private SceneObjectAxis objectAxis;
    private Solid activeSolid;


    private final Scene scene;

    private App() {

        // Singletons
        if (app == null)
            app = this;
        if (gui == null)
            gui = new MainFrame(WIDTH, HEIGHT);

        // Scene init
        scene = new Scene(gui.getCanvas3D(), Scene.Projection.PERSPECTIVE);

        // Models
        TetraHedron th = new TetraHedron(App.IDLE_COLOR, new Vec3D());
        Plane p = new Plane(Color.BLACK, new Vec3D());
        Cube c = new Cube(App.IDLE_COLOR, new Vec3D(-1.9, -1.9, 0));
        BezierCurve bc = new BezierCurve(Color.CYAN, new Vec3D(0, 0, 0),
                new Point3D(-2, 0, -2),
                new Point3D(-1, 2, -1),
                new Point3D(1, 2, 1),
                new Point3D(2, 0, 2), 20);
        CircleCurve cc = new CircleCurve(Color.BLUE, new Vec3D(0, 0, 0), new Point3D(0, 0, 0), 2, 20);
        objectAxis = new SceneObjectAxis(Color.GREEN, new Vec3D());

        scene.addSolid("circle", cc);
        scene.addSolid("axis", objectAxis);
        scene.addSolid("tetrahedron", th);
        scene.addSolid("plane", p);
        scene.addSolid("cube", c);
        scene.addSolid("bezier", bc);

        setActiveSolid(th);
        resetToCamera();
        renderScene();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

    public Solid getActiveSolid() {
        return activeSolid;
    }


    public void switchTo(Solid solid) {
        if (activeSolid != null) {
            activeSolid.setRenderColor(activeSolid.getDefaultColor());
            setActiveSolid(solid);
        }
        renderScene();
    }

    public void renderScene() {
        gui.getCanvas3D().clear();
        scene.render();
        gui.getCanvas3D().debug(activeSolid);
    }

    public void setActiveSolid(Solid activeSolid) {
        this.activeSolid = activeSolid;
        objectAxis.alignFor(activeSolid);
        activeSolid.setRenderColor(App.ACTIVE_COLOR);
    }

    public SceneObjectAxis getObjectAxis() {
        return objectAxis;
    }

    public Scene getScene() {
        return scene;
    }

    public void resetToCamera() {
        for (Solid s : scene.getSolids().values()) {
            s.transform.rotate(Math.PI / 2, 0, 0);
        }
    }

    public static void resetFocus() {
        gui.setFocusable(true);
        gui.requestFocusInWindow();
    }

    public Solid getSolid(String name) {
        return scene.getSolid(name);
    }
}
