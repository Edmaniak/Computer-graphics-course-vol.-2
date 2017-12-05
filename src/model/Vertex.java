package model;

import transforms.Mat4;
import transforms.Point3D;

public class Vertex {
    private final Point3D position;

    public Vertex(Point3D position) {
        this.position = position;
    }

    public Vertex(double x, double y, double z) {
        position = new Point3D(x, y, z);
    }

    public Point3D getPosition() {
        return position;
    }

    public Vertex mul(Mat4 m) {
        return new Vertex(new Point3D(position).mul(m));
    }
}
