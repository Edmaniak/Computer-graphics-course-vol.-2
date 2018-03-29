package renderer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.Part;
import model.Scene;
import model.Vertex;
import model.light.AmbientLight;
import model.light.PointLight;
import model.objects.Solid;
import renderer.raster.Rasterizer;
import renderer.shader.*;
import transforms.*;

public class Renderer {

    private final List<Vertex> vb = new ArrayList<>();

    private Mat4 model = new Mat4Identity();
    private Mat4 view;
    private Mat4 projection;
    private final Rasterizer rasterizer;
    private final ZTest zTest;
    private final ArrayList<PointLight> lights;
    private AmbientLight ambientLight;

    public enum RenderQuality {
        WIRE, FULL, FULL_LIGHTS, WIRE_FULL
    }

    private RenderQuality renderQuality = RenderQuality.FULL;

    public Renderer(BufferedImage img, ArrayList<PointLight> lights, AmbientLight ambientLight) {
        this.zTest = new ZTest(img);
        this.rasterizer = new Rasterizer(zTest, new ShaderColor());
        this.lights = lights;
        this.ambientLight = ambientLight;
    }

    public void renderSolid(Solid sld) {

        //Setting the right shaderModel
        switch (sld.getShaderType()) {
            case COLOR:
                rasterizer.setShader(new ShaderColor());
                break;
            case TEXTURE:
                rasterizer.setShader(new ShaderTexture(sld.getTexture()));
                break;
            case LIGHTABLE:
                rasterizer.setShader(new ShaderLight(lights, new Vec3D(), ambientLight, sld.getMaterial()));
                break;
            case TEX_LIGHTABLE:
                rasterizer.setShader(
                        new ShaderLightTexture(
                                new ShaderLight(
                                        lights, new Vec3D(),
                                        ambientLight,
                                        sld.getMaterial()
                                ),
                                new ShaderTexture(sld.getTexture()))
                );
                break;
            case COL_LIGHTABLE:
                rasterizer.setShader(
                        new ShaderColorLight(
                                new ShaderLight(
                                        lights, new Vec3D(),
                                        ambientLight,
                                        sld.getMaterial()
                                ), new ShaderColor())
                );
                break;

        }

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
                        line(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)));
                    }
                    break;
                }

                case TRIANGLE: {
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 3) {
                        renderTriangle(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)),
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

        for (int i = 0; i < lights.size(); i++) {
            Mat4 matMVP = model.mul(view).mul(projection);
            Point3D position = new Point3D(lights.get(i).getPointPosition());
            lights.set(i, new PointLight(lights.get(i), new Vec3D(position.mul(matMVP).dehomog().get())));
        }

        for (Solid solid : scene.getSolids().values())
            renderSolid(solid);

        zTest.getzBuffer().clear();
    }

    private void line(Vertex origin, Vertex end) {
        rasterizer.rasterize(origin, end);
    }

    private void renderTriangle(Vertex v1, Vertex v2, Vertex v3) {

        // Sorting
        if (v1.getZ() < v2.getZ()) {
            Vertex pom = v1;
            v1 = v2;
            v2 = pom;
        }

        if (v2.getZ() < v3.getZ()) {
            Vertex pom = v2;
            v2 = v3;
            v3 = pom;
        }

        if (v1.getZ() < v2.getZ()) {
            Vertex pom = v1;
            v1 = v2;
            v2 = pom;
        }

        if (v3.getZ() > 0) {
            triangle(v1, v2, v3);
            return;
        }

        if (v2.getZ() > 0) {
            double t = (0 - v3.getZ() / v2.getZ() - v3.getZ());
            double t1 = (0 - v3.getZ()) / v1.getZ() - v3.getZ();
            Vertex d = v2.mul(1 - t1).add(v3.mul(t));
            Vertex e = v1.mul(1 - t).add(v3.mul(t1));
            triangle(v1, v2, d);
            triangle(d, e, v1);
            return;
        }

        if (v1.getZ() > 0) {
            double t = (0 - v2.getZ()) / v2.getZ() - v1.getZ();
            double t1 = (0 - v3.getZ()) / v1.getZ() - v3.getZ();
            Vertex d = v1.mul(1 - t).add(v2.mul(t));
            Vertex e = v1.mul(1 - t1).add(v3.mul(t1));
            triangle(v1, d, e);
        }
    }

    private void triangle(Vertex v1, Vertex v2, Vertex v3) {

        switch (renderQuality) {
            case WIRE:
                rasterizer.rasterizeWire(v1, v2, v3);
                break;
            case FULL:
                rasterizer.rasterize(v1, v2, v3);
                break;
            case WIRE_FULL:
                rasterizer.rasterize(v1, v2, v3);
                rasterizer.rasterizeWire(v1, v2, v3);
                break;
        }

    }

    /**
     * Switch render quality
     * Mainly intended for gui buttons
     */
    public void switchWire() {
        if (renderQuality == RenderQuality.WIRE)
            renderQuality = RenderQuality.FULL;
        else
            renderQuality = RenderQuality.WIRE;
    }

    public void swichtWireFull() {
        if (renderQuality == RenderQuality.WIRE_FULL)
            renderQuality = RenderQuality.FULL;
        else
            renderQuality = RenderQuality.WIRE_FULL;
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

    public void setRenderQuality(RenderQuality renderQuality) {
        this.renderQuality = renderQuality;
    }

    public RenderQuality getRenderQuality() {
        return renderQuality;
    }
}
