package model;

import app.App;
import gui.Canvas;
import model.objects.Solid;
import renderer.Renderer;
import renderer.ZBuffer;
import renderer.raster.RasterizerLine;
import renderer.raster.RasterizerTriangle;
import transforms.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Scene {

    public enum Projection {
        ORTOGRAPHIC, PERSPECTIVE
    }

    private final Map<String, Solid> solids;
    private final Renderer renderer;
    private Projection projection;
    private Camera camera;
    private ZBuffer zb;

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

    public Scene(BufferedImage img, Projection projection) {
        this.projection = projection;
        solids = new HashMap<>();

        zb = new ZBuffer(App.WIDTH, App.HEIGHT);
        RasterizerLine rl = new RasterizerLine(img);
        RasterizerTriangle rt = new RasterizerTriangle(img, zb);

        renderer = new Renderer(rl, rt, zb);
        setProjection(projection);
        camera = new Camera(INITIAL_CAMERA_POSITION, INITIAL_CAMERA_AZIMUTH, INITIAL_CAMERA_ZENITH, 1, true);

    }

    public void render() {
        renderer.setView(new Mat4(camera.getViewMatrix()));
        for (Solid solid : solids.values()) {
            renderer.render(solid);
        }
        zb.clear();
    }

    public void addSolid(String name, Solid solid) {
        solids.put(name, solid);
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

    public Solid getSolid(String name) {
        return solids.get(name);
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
}
