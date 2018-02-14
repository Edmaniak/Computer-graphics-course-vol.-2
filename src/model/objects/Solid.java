package model.objects;

import model.Part;
import model.Transform;
import model.Vertex;
import transforms.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Solid {

    private final List<Vertex> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final List<Part> parts;
    Color renderColor;
    private Color defaultColor;
    public final Transform transform;
    private Vec3D pivotPoint;

    public Solid(Color color, Vec3D pivotPoint, Vec3D initialPosition) {
        this.vertexBuffer = new ArrayList<>();
        this.indexBuffer = new ArrayList<>();
        this.parts = new ArrayList<>();
        this.renderColor = defaultColor = color;
        this.pivotPoint = pivotPoint;
        this.transform = new Transform(pivotPoint);
        transform.translate(initialPosition);
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

    public Color getRenderColor() {
        return renderColor;
    }

    public void setRenderColor(Color renderColor) {
        this.renderColor = renderColor;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public Vec3D getDefaultPivotPoint() {
        return pivotPoint;
    }

    public void setPivotPoint(Vec3D pivotPoint) {
        this.pivotPoint = pivotPoint;
    }

    public void randomizeColors() {
        Random r = new Random();
        for (Vertex v : vertexBuffer) {
            v.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        }
    }
}