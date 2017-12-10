package renderer;

import app.App;
import gui.Canvas;
import model.objects.Solid;
import transforms.*;
import utilities.Angle;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public enum Projection {
        ORTOGRAPHIC, PERSPECTIVE
    }

    private List<Solid> solids;
    private Renderer renderer;
    private Canvas canvas;
    private Projection projection;
    private Camera camera;

    public static double PERSP_VIEW_ANGLE = Math.PI / 4;
    public static double PERSP_NEAR_CLIPPING_PLANE = 0.001;
    public static double PERSP_FAR_CLIPPING_PLANE = 100;

    public static double ORTH_VIEW_CUBOID_WIDTH;
    public static double ORTH_VIEW_CUBOID_HEIGHT;
    public static double ORTH_NEAR_CLIPPING_PLANE;
    public static double ORTH_FAR_CLIPPING_PLANE;

    public Scene(Canvas canvas, Projection projection) {
        this.projection = projection;
        solids = new ArrayList<>();

        RasterizerLine rl = new RasterizerLine(canvas.getMainBuffer());
        RasterizerTriangle rt = new RasterizerTriangle(canvas.getMainBuffer());

        renderer = new Renderer(rl, rt);
        setProjection(projection);
        camera = new Camera(new Vec3D(0,2,2),Math.PI/2, 0,0,true);
        System.out.println(camera.getViewVector());
        renderer.setView(new Mat4ViewRH(new Vec3D(0, 2, 8), new Vec3D(0, -0.05, -1), new Vec3D(0, 1, 0)));

    }

    public void render() {
        for (Solid solid : solids) {
            renderer.render(solid);
        }
    }

    public void addSolid(Solid solid) {
        solids.add(solid);
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
}
