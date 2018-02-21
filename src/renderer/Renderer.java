package renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import app.App;
import material.Material;
import model.Part;
import model.Scene;
import model.Vertex;
import model.light.AmbientLight;
import model.light.Light;
import model.light.PointLight;
import model.objects.Solid;
import renderer.raster.RasterizerLine;
import renderer.raster.RasterizerTriangle;
import transforms.*;
import utilities.Util;

public class Renderer {

    private final List<Vertex> vb = new ArrayList<>();

    private Mat4 model = new Mat4Identity();
    private Mat4 view;
    private Mat4 projection;
    private final RasterizerLine rl;
    private final RasterizerTriangle rt;
    private final ZBuffer zb;
    private final List<PointLight> lights;
    private AmbientLight ambientLight;

    public Renderer(List<PointLight> lights, AmbientLight ambientLight) {
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
                        line(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)), sld.getMaterial());
                    }
                    break;
                }

                case TRIANGLE: {
                    System.out.println(sld);
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 3) {
                        triangle(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)),
                                vb.get(sld.indexes().get(i + 2)), sld.getMaterial());
                    }
                    break;
                }
            }
        }
        vb.clear();
    }


    public void renderVertex() {

    }

    public void renderScene(Scene scene) {

        setView(new Mat4(scene.getCamera().getViewMatrix()));

        for (int i = 0; i < lights.size(); i++) {
            Mat4 matMVP = model.mul(view).mul(projection);
            Point3D position = new Point3D(lights.get(i).getPointPosition());
            lights.set(i, new PointLight(lights.get(i), new Vertex(new Point3D(position.mul(matMVP).dehomog().get()))));

        }


        for (Solid solid : scene.getSolids().values())
            renderSolid(solid);

        zb.clear();
    }

    private void line(Vertex origin, Vertex end, Material material) {

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
        vec1 = Util.project2D(vec1);
        vec2 = Util.project2D(vec2);

        rl.rasterize(vec1, vec2);

    }

    private void triangle(Vertex v1, Vertex v2, Vertex v3, Material material) {

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
        vec1 = Util.project2D(vec1);
        vec2 = Util.project2D(vec2);
        vec3 = Util.project2D(vec3);

        rt.rasterize(vec1, vec2, vec3, material);

    }

    private boolean clipTest(Vertex v) {
        return !(v.getX() < -1) && !(v.getX() > 1) && !(v.getY() < -1) && !(v.getY() > 1) && !(v.getZ() < 0) && !(v.getZ() > 1);
    }

    private boolean isDehomogenizable(Vertex v) {
        return v.getPosition().dehomog().isPresent();
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
