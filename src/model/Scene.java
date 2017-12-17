package model;

import app.App;
import gui.Canvas;
import model.objects.Solid;
import renderer.Renderer;
import renderer.raster.RasterizerLine;
import renderer.raster.RasterizerTriangle;
import transforms.*;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    public enum Projection {
        ORTOGRAPHIC, PERSPECTIVE
    }

    private Map<String, Solid> solids;
    private Renderer renderer;
    private Canvas canvas;
    private Projection projection;
    private Camera camera;

    public static final double PERSP_VIEW_ANGLE = Math.PI / 3;
    public static final double PERSP_NEAR_CLIPPING_PLANE = 0.1;
    public static final double PERSP_FAR_CLIPPING_PLANE = 30;

    public static final double ORTH_VIEW_CUBOID_WIDTH = 10;
    public static final double ORTH_VIEW_CUBOID_HEIGHT = 10;
    public static final double ORTH_NEAR_CLIPPING_PLANE = 0.001;
    public static final double ORTH_FAR_CLIPPING_PLANE = 50;

    public static final double MOVEMENT_STEP = 0.1;
    public static final double TILT_STEP = 0.01;

    public static final Vec3D INITIAL_CAMERA_POSITION = new Vec3D(-0.43, -8.29, 2.59);
    public static final double INITIAL_CAMERA_AZIMUTH = -4.74;
    public static final double INITIAL_CAMERA_ZENITH = -0.2;

    public Scene(Canvas canvas, Projection projection) {
        this.projection = projection;
        solids = new HashMap<>();

        RasterizerLine rl = new RasterizerLine(canvas.getMainBuffer());
        RasterizerTriangle rt = new RasterizerTriangle(canvas.getMainBuffer());

        renderer = new Renderer(rl, rt);
        setProjection(projection);
        camera = new Camera(INITIAL_CAMERA_POSITION, INITIAL_CAMERA_AZIMUTH, INITIAL_CAMERA_ZENITH, 1, true);
        //renderer.setView(new Mat4ViewRH(new Vec3D(0, 2, 8), new Vec3D(0, -0.05, -1), new Vec3D(0, 0, 0)));

    }

    public void render() {
        renderer.setView(new Mat4(camera.getViewMatrix()));
        for (Solid solid : solids.values()) {
            renderer.render(solid);
        }
    }

    public void addSolid(String name, Solid solid) {
        solids.put(name, solid);
    }

    public void setProjection(Projection projection) {
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
        camera = camera.move(new Vec3D(vn.getX(),vn.getY(),0).mul(MOVEMENT_STEP));
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
