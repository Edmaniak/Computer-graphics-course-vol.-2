package renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import app.App;
import model.Parts;
import model.Vertex;
import model.objects.Solid;
import transforms.*;

public class Renderer {

    public List<Vertex> vb = new ArrayList<>();

    private Mat4 model = new Mat4Identity();
    private Mat4 view;
    private Mat4 projection;
    private RasterizerLine rl;
    private RasterizerTriangle rt;


    public Renderer(RasterizerLine rl, RasterizerTriangle rt) {
        this.rl = rl;
        this.rt = rt;
    }

    public void render(Solid sld) {

        // Modelovaci transformace definovana vlastnostmi solidu
        setModel(sld.transform.getModel());

        // Transformace
        Mat4 matMVP = model.mul(view).mul(projection);

        for (Vertex v : sld.vertices())
            vb.add(v.mul(matMVP));
        for (Parts p : sld.getParts()) {

            switch (p.getType()) {

                case LINE: {
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 2) {
                        line(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)), sld.getColor());
                    }
                    break;
                }

                case TRIANGLE: {
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 3) {
                        triangle(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)),
                                vb.get(sld.indexes().get(i + 2)), sld.getColor());
                    }
                    break;
                }
            }
        }
        vb.clear();
    }

    private void line(Vertex origin, Vertex end, Color color) {
        // clipp

        // dehomogenizace
        if (!isDehomogenizable(origin) || !isDehomogenizable(end))
            return;

        Vec3D v1 = project2D(origin.dehomog());
        Vec3D v2 = project2D(end.dehomog());

        rl.draw(v1, v2, color);

        // System.out.println("O: " + origin + " " + "E: " + end);
    }

    private void triangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        //clip

        //dehomogenizace
        if (!isDehomogenizable(v1) || !isDehomogenizable(v2) || !isDehomogenizable(v3))
            return;

        Vec3D vec1 = project2D(v1.dehomog());
        Vec3D vec2 = project2D(v2.dehomog());
        Vec3D vec3 = project2D(v3.dehomog());

        rt.draw(vec1, vec2, vec3, color);

        // System.out.println("v1: " + v1 + " v2: " + v2 + " v3 " + v3);
    }


    private boolean isDehomogenizable(Vertex v) {
        return v.getPosition().dehomog().isPresent();
    }

    private Vec3D project2D(Vec3D v) {
        double y = (-v.getY() + 1) * App.HEIGHT / 2;
        double x = (v.getX() + 1) * App.WIDTH / 2;
        return new Vec3D(x, y, 0);
    }


    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

}
