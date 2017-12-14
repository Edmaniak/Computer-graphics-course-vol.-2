package app;

import gui.MainFrame;
import model.BezierCurve;
import model.objects.*;
import renderer.Scene;
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
        Cube c = new Cube(App.IDLE_COLOR, new Vec3D(-1.9, 0, -1.9));
        BezierCurve bc = new BezierCurve(Color.CYAN,new Vec3D(0,0,0),
                new Point3D(-2,0,-2),
                new Point3D(-1,0,-1),
                new Point3D(1,0,1),
                new Point3D(2,0,2));
        objectAxis = new SceneObjectAxis(Color.GREEN, new Vec3D());

        scene.addSolid("axis", objectAxis);
        scene.addSolid("tetrahedron", th);
        scene.addSolid("plane", p);
        scene.addSolid("cube", c);
        c.transform.translateToOrigin();
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
            activeSolid.setColor(App.IDLE_COLOR);
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
        activeSolid.setColor(App.ACTIVE_COLOR);
    }

    public SceneObjectAxis getObjectAxis() {
        return objectAxis;
    }

    public Scene getScene() {
        return scene;
    }
    
    public void resetToCamera() {
    	for(Solid s : scene.getSolids().values()) {
    		s.transform.rotate(0, 0, 45);
    	}
    }

    public Solid getSolid(String name) {
        return scene.getSolid(name);
    }
}
