package app;

import gui.MainFrame;
import material.Material;
import material.Texture;
import model.light.PointLight;
import model.objects.*;
import model.Scene;
import transforms.Col;
import transforms.Vec3D;


import javax.swing.*;
import java.awt.*;

public class App {

    public static final String title = "D 3D TABLE";
    public static final int WIDTH = 1055;
    public static final int HEIGHT = 800;
    public static final int RATIO = WIDTH / HEIGHT;

    private static MainFrame gui;
    public static App app;
    private final SceneObjectAxis objectAxis;
    private SceneObject activeObject;

    private final Scene scene;

    private App() {

        // Singletons
        if (app == null)
            app = this;
        if (gui == null)
            gui = new MainFrame(WIDTH, HEIGHT, this);

        // Scene init
        scene = new Scene(gui.getCanvas3D().getMainBuffer(), Scene.Projection.PERSPECTIVE);

        //Materials
        Material brass = new Material(0.3, 0.9, 0.6, 27);
        Material difuse = new Material(0.3, 0, 0.7, 20);

        // Models
        Texture bg = new Texture("res/fim.jpg");
        Plane plane = new Plane(brass, bg, new Vec3D());
        plane.transform.rotate(Math.PI / 2, 0, 0);
        plane.transform.translate(new Vec3D(0, 0, 2));

        Cube cube = new Cube(brass, new Vec3D(-1.9, -1.9, 0));
        objectAxis = new SceneObjectAxis(new Col(0, 255, 0), new Vec3D());
        cube.randomizeColors();

        BicubicPlate plate = new BicubicPlate(brass,new Vec3D(0, 0, 1), 5);
        plate.transform.translate(new Vec3D(0, 0, -1));
        plate.setShaderType(Solid.ShaderType.COL_LIGHTABLE);


        // Lights
        PointLight light1 = new PointLight(new Col(0, 255, 0), new Vec3D(0, -1, 1), 0.2);
        Cube lightCube = new Cube(difuse, light1.getPosition());


        scene.addSolid("plane", plane);
        scene.addSolid("axis", objectAxis);
        scene.addSolid("cube", cube);
        scene.addSolid("bicubic", plate);

        scene.addLight("light1", light1);


        setActiveObject(cube);

        // HACK Protoze camera je v jinych souradnicich
        resetToCamera();
        renderScene();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

    public SceneObject getActiveObject() {
        return activeObject;
    }

    public void switchTo(SceneObject solid) {
        if (activeObject != null)
            setActiveObject(solid);
        renderScene();
    }

    public void switchTo(String string) {
        if (activeObject != null)
            setActiveObject(scene.getSceneObject(string));
        renderScene();
    }

    public void renderScene() {
        gui.getCanvas3D().clear();
        scene.render();
        gui.getCanvas3D().debug(activeObject);
    }

    private void setActiveObject(SceneObject activeObject) {
        this.activeObject = activeObject;
        objectAxis.alignFor(activeObject);
    }

    public SceneObjectAxis getObjectAxis() {
        return objectAxis;
    }

    public Scene getScene() {
        return scene;
    }

    private void resetToCamera() {
        for (SceneObject s : scene.getSceneObjects().values()) {
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

}
