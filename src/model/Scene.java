package model;

import app.App;
import model.light.AmbientLight;
import model.light.PointLight;
import model.objects.SceneObject;
import model.objects.Solid;
import renderer.Renderer;
import transforms.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {


    public enum Projection {
        ORTOGRAPHIC, PERSPECTIVE
    }

    private final Map<String, Solid> solids;
    private final Map<String, SceneObject> sceneObjects;
    private final ArrayList<PointLight> lights;
    private final Renderer renderer;
    private Projection projection;
    private Camera camera;

    private static final double PERSP_VIEW_ANGLE = Math.PI / 3;
    private static final double PERSP_NEAR_CLIPPING_PLANE = 0.1;
    private static final double PERSP_FAR_CLIPPING_PLANE = 30;

    private static final double ORTH_VIEW_CUBOID_WIDTH = 10;
    private static final double ORTH_VIEW_CUBOID_HEIGHT = 10;
    private static final double ORTH_NEAR_CLIPPING_PLANE = 0.001;
    private static final double ORTH_FAR_CLIPPING_PLANE = 50;

    private static final double MOVEMENT_STEP = 0.1;
    private static final double TILT_STEP = 0.01;

    private static final Vec3D INITIAL_CAMERA_POSITION = new Vec3D(-0.43, -8.29, 2.59);
    private static final double INITIAL_CAMERA_AZIMUTH = -4.74;
    private static final double INITIAL_CAMERA_ZENITH = -0.2;

    private AmbientLight ambientLight = new AmbientLight(new Col(255, 255, 255), 0.3);

    public Scene(BufferedImage img, Projection projection) {
        this.projection = projection;
        solids = new HashMap<>();
        sceneObjects = new HashMap<>();
        lights = new ArrayList<>();
        renderer = new Renderer(img, lights, ambientLight);
        setProjection(projection);
        camera = new Camera(INITIAL_CAMERA_POSITION, INITIAL_CAMERA_AZIMUTH, INITIAL_CAMERA_ZENITH, 1, true);
    }

    public Scene(BufferedImage img, Projection projection, AmbientLight ambientLight) {
        this(img, projection);
        this.ambientLight = ambientLight;
    }

    public void render() {
        renderer.renderScene(this);
    }

    public void addSolid(String name, Solid solid) {
        solids.put(name, solid);
        addSceneObject(name, solid);
    }

    public void addSceneObject(String name, SceneObject object) {
        sceneObjects.put(name, object);
    }

    public void addLight(String name, PointLight light) {
        lights.add(light);
        sceneObjects.put(name, light);
    }

    private void setProjection(Projection projection) {
        this.projection = projection;
        switch (projection) {
            case PERSPECTIVE:
                renderer.setProjection(new Mat4PerspRH(PERSP_VIEW_ANGLE, App.RATIO, PERSP_NEAR_CLIPPING_PLANE, PERSP_FAR_CLIPPING_PLANE));
                break;
            case ORTOGRAPHIC:
                renderer.setProjection(new Mat4OrthoRH(ORTH_VIEW_CUBOID_WIDTH, ORTH_VIEW_CUBOID_HEIGHT, ORTH_NEAR_CLIPPING_PLANE, ORTH_FAR_CLIPPING_PLANE));
                break;
        }
    }

    public SceneObject getSceneObject(String name) {
        return sceneObjects.get(name);
    }


    public void moveRight() {
        camera = camera.right(MOVEMENT_STEP);
    }

    public void moveLeft() {
        camera = camera.left(MOVEMENT_STEP);
    }

    public void moveForward() {
        camera = camera.forward(MOVEMENT_STEP);
    }

    public void moveBackwards() {
        camera = camera.backward(MOVEMENT_STEP);
    }

    public void moveUp() {
        camera = camera.up(MOVEMENT_STEP);
    }

    public void moveDown() {
        camera = camera.down(MOVEMENT_STEP);
    }

    public void tiltLeft() {
        camera = camera.addAzimuth(TILT_STEP);
    }

    public void tiltRight() {
        camera = camera.addAzimuth(-TILT_STEP);
    }

    public void tiltUp() {
        camera = camera.addZenith(TILT_STEP);
    }

    public void tiltDown() {
        camera = camera.addZenith(-TILT_STEP);
    }

    public void tilt(Vec2D v) {
        if (!v.normalized().isPresent())
            return;
        Vec2D vn = v.normalized().get();
        camera = camera.addZenith(vn.getY() * TILT_STEP);
        camera = camera.addAzimuth(vn.getX() * TILT_STEP);
    }

    public void move(Vec2D v) {
        if (!v.normalized().isPresent())
            return;
        Vec2D vn = v.normalized().get();
        camera = camera.move(new Vec3D(vn.getX(), vn.getY(), 0).mul(MOVEMENT_STEP));
    }


    public Camera getCamera() {
        return camera;
    }


    public Map<String, Solid> getSolids() {
        return solids;
    }

    public void switchProjection() {
        if (projection == Projection.PERSPECTIVE)
            setProjection(Projection.ORTOGRAPHIC);
        else
            setProjection(Projection.PERSPECTIVE);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public List<PointLight> getLights() {
        return lights;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    public Map<String,SceneObject> getSceneObjects() {
        return sceneObjects;
    }
}
