package model.objects;

import model.Parts;
import model.Transform;
import model.Vertex;
import transforms.Point2D;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solid {

    private final List<Vertex> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final List<Parts> parts;
    protected Color color;
    public final Transform transform;
    private Vec3D pivotPoint;

    protected Solid(Color color, Vec3D pivotPoint,Vec3D initialPosition) {
        this.vertexBuffer = new ArrayList<>();
        this.indexBuffer = new ArrayList<>();
        this.parts = new ArrayList<>();
        this.color = color;
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
    public List<Parts> getParts() {
        return parts;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vec3D getDefaultPivotPoint() {
        return pivotPoint;
    }

    public void setPivotPoint(Vec3D pivotPoint) {
        this.pivotPoint = pivotPoint;
    }
}