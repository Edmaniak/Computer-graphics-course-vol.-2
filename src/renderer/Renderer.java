package renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import app.App;
import model.Part;
import model.Scene;
import model.Vertex;
import model.light.PointLight;
import model.objects.Solid;
import renderer.raster.RasterizerLine;
import renderer.raster.RasterizerTriangle;
import transforms.*;

public class Renderer {

    private final List<Vertex> vb = new ArrayList<>();

    private Mat4 model = new Mat4Identity();
    private Mat4 view;
    private Mat4 projection;
    private final RasterizerLine rl;
    private final RasterizerTriangle rt;
    private final ZBuffer zb;
    private final List<PointLight> lights;
    private double ambientLight;

    public Renderer(List<PointLight> lights, double ambientLight) {
        this.zb = new ZBuffer(App.WIDTH, App.HEIGHT);
        this.rl = new RasterizerLine(App.gui.getCanvas3D().getMainBuffer(), zb);
        this.rt = new RasterizerTriangle(App.gui.getCanvas3D().getMainBuffer(), zb, lights, ambientLight);
        this.lights = lights;
        this.ambientLight = ambientLight;
    }

    public void renderSolid(Solid sld) {

        // Modelovaci transformace definovana vlastnostmi solidu
        setModel(sld.transform.getModel());

        // Transformace pohledu a projekce
        Mat4 matMVP = model.mul(view).mul(projection);

        for (Vertex v : sld.vertices())
            vb.add(v.mul(matMVP));
        for (Part p : sld.parts()) {

            switch (p.getType()) {

                case LINE: {
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 2) {
                        line(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)), sld.getRenderColor());
                    }
                    break;
                }

                case TRIANGLE: {
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 3) {
                        triangle(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)),
                                vb.get(sld.indexes().get(i + 2)));
                    }
                    break;
                }
            }
        }
        vb.clear();
    }

    public void renderScene(Scene scene) {
        setView(new Mat4(scene.getCamera().getViewMatrix()));
        for (Solid solid : scene.getSolids().values())
            renderSolid(solid);
        zb.clear();

    }

    private void line(Vertex origin, Vertex end, Color color) {

        // dehomogenizace
        if (!isDehomogenizable(origin) || !isDehomogenizable(end))
            return;

        Vertex vec1 = origin.dehomog();
        Vertex vec2 = end.dehomog();

        // orez
        if (!clipTest(vec1))
            return;
        if (!clipTest(vec2))
            return;

        // projekce do rastru
        vec1 = project2D(vec1);
        vec2 = project2D(vec2);

        rl.rasterize(vec1, vec2);

    }

    private void triangle(Vertex v1, Vertex v2, Vertex v3) {

        //dehomogenizace
        if (!isDehomogenizable(v1) || !isDehomogenizable(v2) || !isDehomogenizable(v3))
            return;

        Vertex vec1 = v1.dehomog();
        Vertex vec2 = v2.dehomog();
        Vertex vec3 = v3.dehomog();

        // clip
        if (!clipTest(vec1))
            return;
        if (!clipTest(vec2))
            return;
        if (!clipTest(vec3))
            return;

        // projekce do rastru
        vec1 = project2D(vec1);
        vec2 = project2D(vec2);
        vec3 = project2D(vec3);


        rt.rasterize(vec1, vec2, vec3);

    }

    private boolean clipTest(Vertex v) {
        return !(v.getX() < -1) && !(v.getX() > 1) && !(v.getY() < -1) && !(v.getY() > 1) && !(v.getZ() < 0) && !(v.getZ() > 1);
    }

    private boolean isDehomogenizable(Vertex v) {
        return v.getPosition().dehomog().isPresent();
    }

    private Vertex project2D(Vertex v) {
        double y = (-v.getY() + 1) * App.HEIGHT / 2;
        double x = (v.getX() + 1) * App.WIDTH / 2;
        return new Vertex(x, y, v.getZ(), v.getColor());
    }

    private void setModel(Mat4 model) {
        this.model = model;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    public Mat4 getView() {
        return view;
    }
}
