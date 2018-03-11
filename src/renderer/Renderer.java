package renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
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
import renderer.raster.Rasterizer;
import renderer.raster.RasterizerLine;
import renderer.raster.RasterizerSchool;
import renderer.raster.RasterizerTriangle;
import renderer.shader.ShaderColor;
import renderer.shader.ShaderLight;
import renderer.shader.ShaderTexture;
import transforms.*;
import utilities.Util;

public class Renderer {

    private final List<Vertex> vb = new ArrayList<>();

    private Mat4 model = new Mat4Identity();
    private Mat4 view;
    private Mat4 projection;
    // private final RasterizerLine rl;
    private final RasterizerSchool rasterizer;
    private final ZTest zTest;
    private final ArrayList<PointLight> lights;
    private AmbientLight ambientLight;

    public enum RenderQuality {
        WIRE, FULL, FULL_LIGHTS, WIRE_FULL
    }

    private RenderQuality renderQuality = RenderQuality.FULL;

    public Renderer(BufferedImage img, ArrayList<PointLight> lights, AmbientLight ambientLight) {
        this.zTest = new ZTest(img);
        //this.rl = new RasterizerLine(App.gui.getCanvas3D().getMainBuffer(), zb);
        this.rasterizer = new RasterizerSchool(zTest, new ShaderColor());
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
                rasterizer.setShader(new ShaderLight(lights,new Vec3D(),ambientLight,sld.getMaterial()));
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
                        line(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)), sld.getEditorColor());
                    }
                    break;
                }

                case TRIANGLE: {
                    for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 3) {
                        triangle(vb.get(sld.indexes().get(i)), vb.get(sld.indexes().get(i + 1)),
                                vb.get(sld.indexes().get(i + 2)), sld.getEditorColor());
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
            lights.set(i, new PointLight(lights.get(i), new Vertex(new Point3D(position.mul(matMVP).dehomog().get()))));
        }

        for (Solid solid : scene.getSolids().values())
            renderSolid(solid);

        zTest.getzBuffer().clear();
    }

    private void line(Vertex origin, Vertex end, Col color) {
        // TODO orez

        rasterizer.rasterize(origin, end);
    }

    private void triangle(Vertex v1, Vertex v2, Vertex v3, Col color) {
        // TODO orez

        switch (renderQuality) {
            case WIRE:
                rasterizer.rasterizeWire(v1, v2, v3, color);
                break;
            case FULL:
                rasterizer.rasterize(v1, v2, v3);
                break;
            case WIRE_FULL:
                rasterizer.rasterize(v1,v2,v3);
                rasterizer.rasterizeWire(v1, v2, v3, color);
                break;
        }

    }

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
