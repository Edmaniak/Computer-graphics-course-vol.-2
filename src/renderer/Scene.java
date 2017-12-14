package renderer;

import app.App;
import gui.Canvas;
import model.objects.SceneCamera;
import model.objects.Solid;
import transforms.*;
import utilities.Angle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    double i = 0;

    public static double PERSP_VIEW_ANGLE = Math.PI / 4;
    public static double PERSP_NEAR_CLIPPING_PLANE = 0.001;
    public static double PERSP_FAR_CLIPPING_PLANE = 100;

    public static double ORTH_VIEW_CUBOID_WIDTH;
    public static double ORTH_VIEW_CUBOID_HEIGHT;
    public static double ORTH_NEAR_CLIPPING_PLANE;
    public static double ORTH_FAR_CLIPPING_PLANE;

    public Scene(Canvas canvas, Projection projection) {
        this.projection = projection;
        solids = new HashMap<>();

        RasterizerLine rl = new RasterizerLine(canvas.getMainBuffer());
        RasterizerTriangle rt = new RasterizerTriangle(canvas.getMainBuffer());

        renderer = new Renderer(rl, rt);
        setProjection(projection);
        camera = new Camera();
        camera = camera.withPosition(new Vec3D(-30,0,0));
        camera = camera.withZenith(Math.toRadians(0));
      
        renderer.setView(camera.getViewMatrix());
        //renderer.setView(new Mat4ViewRH(new Vec3D(0, 2, 8), new Vec3D(0, -0.05, -1), new Vec3D(0, 0, 0)));

    }

    public void render() {
        //camera = camera.addAzimuth(1.534);
        //Mat4 mat = new Mat4(camera.getViewMatrix());
        //System.out.println(mat);
        //renderer.setView(new Mat4(camera.getViewMatrix()));
        //renderer.setView(new Mat4ViewRH(new Vec3D(0, 2, 8), new Vec3D(0, -0.05, -1), new Vec3D(0, 1, 0)));
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

    public Camera getCamera() {
        return camera;
    }
    
    public Map<String, Solid> getSolids() {
		return solids;
	}


    public Renderer getRenderer() {
        return renderer;
    }
}
