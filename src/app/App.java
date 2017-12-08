package app;

import gui.MainFrame;
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

    private final Scene scene;

    private App() {

        // Singletons
        if (app == null)
            app = this;
        if (gui == null)
            gui = new MainFrame(WIDTH, HEIGHT);

        // Scene
        scene = new Scene(gui.getCanvas(), Scene.Projection.PERSPECTIVE);

        // Models
        TetraHedron th = new TetraHedron(new Color(70,73,76));
        scene.addSolid(th);
        scene.render();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

    public Scene scene() {
        return scene;
    }
}
