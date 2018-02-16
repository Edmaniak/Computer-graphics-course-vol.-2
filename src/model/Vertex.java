package model;

import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class Vertex {
    private final Point3D position;
    private Color color = Color.WHITE;

    public Vertex(Point3D position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Vertex(Vec3D position, Color color) {
        this.position = new Point3D(position.getX(), position.getY(), position.getZ());
        this.color = color;
    }


    public Vertex(Point3D position) {
        this.position = position;
    }

    public Vertex(double x, double y, double z, Color color) {
        this.position = new Point3D(x, y, z);
        this.color = color;
    }

    public Vertex(double x, double y, double z) {
        this.position = new Point3D(x, y, z);
        this.color = color;
    }



    public Point3D getPosition() {
        return position;
    }

    public Vertex mul(Mat4 m) {
        return new Vertex(new Point3D(position).mul(m));
    }

    public Vertex dehomog() {
        Vec3D pos = position.dehomog().get();
        return new Vertex(pos, this.color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return position.toString();
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }
}
