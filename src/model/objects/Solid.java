package model.objects;

import material.Material;
import material.Texture;
import model.Part;
import model.Vertex;
import renderer.shader.Shader;
import transforms.Col;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Solid extends SceneObject {

    private final List<Vertex> vertexBuffer = new ArrayList<>();
    private final List<Integer> indexBuffer = new ArrayList<>();
    private final List<Part> parts = new ArrayList<>();

    public enum ShaderType {
        COLOR, TEXTURE, TEX_LIGHTABLE, COL_LIGHTABLE, LIGHTABLE
    }

    private ShaderType shaderType;
    private Material material = new Material();
    private Texture texture;

    public Solid(Vec3D pivotPoint, Vec3D initialPosition) {
        super(pivotPoint, initialPosition);
        this.shaderType = ShaderType.COLOR;
    }

    public Solid(Material material, Vec3D pivotPoint, Vec3D initialPosition) {
        this(pivotPoint, initialPosition);
        this.material = material;
        this.shaderType = ShaderType.COL_LIGHTABLE;
    }

    public Solid(Material material, Texture texture, Vec3D initialPosition) {
        this(texture, initialPosition);
        this.material = material;
        this.shaderType = ShaderType.TEX_LIGHTABLE;
    }

    public Solid(Vec3D initialPosition) {
        super(initialPosition);
        this.shaderType = ShaderType.COLOR;
    }

    public Solid(Texture tex, Vec3D initialPosition) {
        super(initialPosition);
        this.texture = tex;
        this.shaderType = ShaderType.TEXTURE;
    }

    public List<Vertex> vertices() {
        return vertexBuffer;
    }

    public List<Integer> indexes() {
        return indexBuffer;
    }

    public List<Part> parts() {
        return parts;
    }

    public void randomizeColors() {
        Random r = new Random();
        for (Vertex v : vertexBuffer) {
            v.setColor(new Col(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        }
    }

    /**
     * Randomize vertex colors with constrains as the max values for
     * individual color channels
     *
     * @param consRed
     * @param consGreen
     * @param consBlue
     */
    public void randomizeColors(int consRed, int consGreen, int consBlue) {
        Random r = new Random();
        for (Vertex v : vertexBuffer) {
            v.setColor(new Col(
                    r.nextInt(consRed),
                    r.nextInt(consGreen),
                    r.nextInt(consBlue)
            ));
        }
    }

    /**
     * We define solids in a very bad way. Since buffers are set as array fields :(
     * This function builds the child's specific geometry after super() call
     *
     * @param vertexBuffer
     * @param indexBuffer
     * @param parts
     */
    protected void define(Vertex[] vertexBuffer, Integer[] indexBuffer, Part[] parts) {
        vertices().addAll(Arrays.asList(vertexBuffer));
        indexes().addAll(Arrays.asList(indexBuffer));
        parts().addAll(Arrays.asList(parts));
        calculateNormals();
    }

    public void calculateNormals() {
        Vec3D n1 = new Vec3D();
        Vec3D n2 = new Vec3D();
        Vec3D n3 = new Vec3D();
        for (Part p : parts) {
            if (p.getType() == Part.Type.TRIANGLE) {
                for (int i = p.getStart(); i < (p.getCount() + p.getStart()); i += 3) {

                    Vertex v1 = new Vertex(vertices().get(indexes().get(i)));
                    Vertex v2 = new Vertex(vertices().get(indexes().get(i + 1)));
                    Vertex v3 = new Vertex(vertices().get(indexes().get(i + 2)));

                    Vec3D AB = v2.getPositionVec().sub(v1.getPositionVec());
                    Vec3D AC = v3.getPositionVec().sub(v1.getPositionVec());

                    Vec3D normal = Util.crossProduct(AB, AC).normalized().get();


                    n1 = new Vec3D(v1.getNormal().add(normal));
                    n2 = new Vec3D(v2.getNormal().add(normal));
                    n3 = new Vec3D(v3.getNormal().add(normal));


                    vertices().set(indexes().get(i), new Vertex(v1, n1));
                    vertices().set(indexes().get(i + 1), new Vertex(v2, n2));
                    vertices().set(indexes().get(i + 2), new Vertex(v3, n3));
                }
            }
        }

    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ShaderType getShaderType() {
        return shaderType;
    }

    public void setShaderType(ShaderType shaderType) {
        this.shaderType = shaderType;
    }

    public Texture getTexture() {
        return texture;
    }

}