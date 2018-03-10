package model.objects;

import material.Material;
import material.Texture;
import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Solid extends SceneObject {

    private final List<Vertex> vertexBuffer = new ArrayList<>();
    private final List<Integer> indexBuffer = new ArrayList<>();
    private final List<Part> parts = new ArrayList<>();

    private Material material;
    private Texture texture;

    private Solid(Vec3D pivotPoint, Vec3D initialPosition) {
        super(pivotPoint, initialPosition);
    }

    public Solid(Material material, Vec3D pivotPoint, Vec3D initialPosition) {
        this(pivotPoint, initialPosition);
        this.material = material;
    }

    public Solid(Color color, Vec3D pivotPoint, Vec3D initialPosition) {
        this(pivotPoint, initialPosition);
        this.material = new Material(color);
    }

    public Solid(Color color, Vec3D initialPosition) {
        super(initialPosition);
        this.material = new Material(color);
    }
    public Solid(Texture tex, Vec3D initialPosition) {
        super(initialPosition);
        this.texture = tex;
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


    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void randomizeColors() {
        Random r = new Random();
        for (Vertex v : vertexBuffer) {
            v.setColor(new Col(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        }
    }

    public void randomizeColors(int consRed, int consGreen, int consBlue) {
        Random r = new Random();
        for (Vertex v : vertexBuffer) {
            v.setColor(new Col(
                    r.nextInt(consRed) ,
                    r.nextInt(consGreen) ,
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
    }
}