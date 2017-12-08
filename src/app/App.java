package app;

import gui.MainFrame;
import model.objects.Plane;
import model.objects.Solid;
import model.objects.TetraHedron;
import renderer.Scene;

import javax.swing.*;
import java.awt.*;

public class App {

    public static final String title = "SimpleDraw";
    public static MainFrame gui;
    public static App app;
    public static final int WIDTH = 1055;
    public static final int HEIGHT = 800;
    public static final int RATIO = WIDTH / HEIGHT;
    private Solid activeSolid;


    private final Scene scene;

    private App() {

        // Singletons
        if (app == null)
            app = this;
        if (gui == null)
            gui = new MainFrame(WIDTH, HEIGHT);

        // Scene init
        scene = new Scene(gui.getCanvas(), Scene.Projection.PERSPECTIVE);

        // Models
        TetraHedron th = new TetraHedron(new Color(70,73,76));
        Plane p = new Plane(Color.BLACK);
        scene.addSolid(th);
        activeSolid = th;
        scene.addSolid(p);

        scene.render();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

    public Scene scene() {
        return scene;
    }

    public Solid getActiveSolid() {
        return activeSolid;
    }

    public void renderScene() {
        gui.getCanvas().clear();
        scene.render();

    }

    public void setActiveSolid(Solid activeSolid) {
        this.activeSolid = activeSolid;
    }
}
