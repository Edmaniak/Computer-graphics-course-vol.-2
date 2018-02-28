package model;

import transforms.Mat4;
import transforms.Point2D;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class Vertex {

    private final Point3D position;
    private Color color = Color.WHITE; //add final def.
    private Vec3D normal;
    private Point2D texUV;
    private double one;

    public Vertex(Point3D position, Color color, Vec3D normal, Point2D texUV, double one) {
        this.position = position;
        this.color = color;
        this.normal = normal;
        this.texUV = texUV;
        this.one = 1.0;
    }

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

    public Vertex normalize() {
        return new Vertex(new Point3D(new Vec3D(position).normalized().get()));
    }

    public Point3D getPosition() {
        return position;
    }

    public Vertex mul(Mat4 m) {
        return new Vertex(new Point3D(position).mul(m));
    }

    public Vertex mul(double t) {
        return new Vertex(position.mul(t), color.mul(t), normal.mul(t), texUV.mul(t), one * t);
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

    public Vec3D getNormal() {
        return normal;
    }

    public Point2D getTexUV() {
        return texUV;
    }
}
