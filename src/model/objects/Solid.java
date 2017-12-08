package model.objects;

import model.Parts;
import model.Vertex;
import transforms.Point2D;
import transforms.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solid {

    private final List<Vertex> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final List<Parts> parts;
    protected Color color;
    private Vec3D position = new Vec3D();
    private double rotY = 0;
    private double rotX = 0;
    private double rotZ = 0;
    private double size = 0;

    public Solid(Color color) {
        this.vertexBuffer = new ArrayList<>();
        this.indexBuffer = new ArrayList<>();
        this.parts = new ArrayList<>();
        this.color = color;
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

    public Vec3D getPosition() {
        return position;
    }

    public void setPosition(Vec3D position) {
        if(position.getY() < 0) {
            this.position = new Vec3D(position.getX(), 0, position.getZ());
            return;
        }
        this.position = position;
    }

    public double getRotY() {
        return rotY;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
    }

    public double getRotX() {
        return rotX;
    }

    public void setRotX(double rotX) {
        this.rotX = rotX;
    }

    public double getRotZ() {
        return rotZ;
    }

    public void setRotZ(double rotZ) {
        this.rotZ = rotZ;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}