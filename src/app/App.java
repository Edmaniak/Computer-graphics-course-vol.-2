package app;

import gui.MainFrame;
import model.objects.*;
import renderer.Scene;

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
    private Axis singleObjectAxis;
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
        TetraHedron th = new TetraHedron(App.IDLE_COLOR);
        Plane p = new Plane(Color.BLACK);
        Cube c = new Cube(App.IDLE_COLOR);
        c.transform.translate(-1.9, 0, -1.9);
        singleObjectAxis = new Axis(Color.GREEN);
        scene.addSolid(singleObjectAxis);
        scene.addSolid(th);
        scene.addSolid(p);
        scene.addSolid(c);

        setActiveSolid(c);

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
            activeSolid = solid;
        }
        renderScene();
    }

    public void renderScene() {
        gui.getCanvas3D().clear();
        scene.render();
        gui.getCanvas3D().debug(activeSolid.transform.getModel());

    }

    public void setActiveSolid(Solid activeSolid) {
        this.activeSolid = activeSolid;
        singleObjectAxis.transform.translate(activeSolid.transform.getWorldPosition());
        activeSolid.setColor(App.ACTIVE_COLOR);
    }

    public Axis getSingleAxis() {
        return singleObjectAxis;
    }
}
