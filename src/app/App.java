package app;

import gui.MainFrame;
import material.Material;
import model.light.PointLight;
import model.objects.*;
import model.Scene;
import transforms.Point3D;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;

public class App {

    public static final String title = "D 3D TABLE";
    private static final Color ACTIVE_COLOR = Color.RED;
    private static final Color IDLE_COLOR = Color.WHITE;
    public static final int WIDTH = 1055;
    public static final int HEIGHT = 800;
    public static final int RATIO = WIDTH / HEIGHT;

    public static MainFrame gui;
    public static App app;
    private final SceneObjectAxis objectAxis;
    private Solid activeSolid;

    private final Scene scene;

    private App() {

        // Singletons
        if (app == null)
            app = this;
        if (gui == null)
            gui = new MainFrame(WIDTH, HEIGHT);

        // Scene init
        scene = new Scene(Scene.Projection.PERSPECTIVE);

        //Materials
        Material brass = new Material(Color.YELLOW, 0.3, 0.9, 0.6, 27);
        Material difuse = new Material(Color.RED, 0.3, 0, 0.7, 20);

        // Models
        TetraHedron tetraHedron = new TetraHedron(difuse, new Vec3D());
        tetraHedron.randomizeColors();
        Plane plane = new Plane(difuse, new Vec3D());
        Cube cube = new Cube(brass, new Vec3D(-1.9, -1.9, 0));
        // IcoSphere icoSphere = new IcoSphere(Color.CYAN, new Vec3D(0, 0, 0), 1);
        objectAxis = new SceneObjectAxis(Color.GREEN, new Vec3D());


        // Lights
        PointLight light1 = new PointLight(Color.WHITE, new Vec3D(0, 0, 2), 1);
        Cube lightCube = new Cube(difuse, light1.getPosition());


        cube.randomizeColors();
        tetraHedron.randomizeColors();

        //scene.addSolid("circle", circleCurve);
        scene.addSolid("axis", objectAxis);
        scene.addSolid("tetrahedron", tetraHedron);
        scene.addSolid("plane", plane);
        scene.addSolid("cube", cube);
        scene.addSolid("lightCube", lightCube);
        //scene.addSolid("icosphere", icoSphere);
        scene.addLight(light1);
        //scene.addSolid("bezier", bezierCurve);

        setActiveSolid(tetraHedron);

        // Protoze camera je v jinych souradnicich
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
            setActiveSolid(solid);
        }
        renderScene();
    }

    public void renderScene() {
        gui.getCanvas3D().clear();
        scene.render();
        gui.getCanvas3D().debug(activeSolid);
    }

    private void setActiveSolid(Solid activeSolid) {
        this.activeSolid = activeSolid;
        objectAxis.alignFor(activeSolid);
    }

    public SceneObjectAxis getObjectAxis() {
        return objectAxis;
    }

    public Scene getScene() {
        return scene;
    }

    private void resetToCamera() {
        for (Solid s : scene.getSolids().values()) {
            s.transform.rotate(Math.PI / 2, 0, 0);
        }
    }

    /**
     * Jinak klik pohřbí klávesnici, nutno volat po interakci s gui
     */
    public static void resetFocus() {
        gui.setFocusable(true);
        gui.requestFocusInWindow();
    }

    public Solid getSolid(String name) {
        return scene.getSolid(name);
    }
}
