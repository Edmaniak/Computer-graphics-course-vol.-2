package model;

import transforms.*;

import java.awt.*;
import java.util.Optional;

public class Vertex {

    private final Point3D position;
    private Col color;
    private final Vec3D normal;
    private final Point2D texUV;
    private double one;

    public Vertex(Point3D position, Col color, Vec3D normal, Point2D texUV, double one) {
        this.position = position;
        this.color = color;
        this.normal = normal;
        this.texUV = texUV;
        this.one = one;
    }

    public Vertex(Point3D position, Col color, Vec3D normal, Point2D texUV) {
        this(position, color, normal, texUV, 1);
    }

    public Vertex(Point3D position, Col color) {
        this(position, color, new Vec3D(), new Point2D());
    }

    public Vertex(Point3D position) {
        this(position, new Col(0, 0, 0));
    }

    public Vertex(Vertex v) {
        this(v.getPosition(), v.getColor(), v.getNormal(), v.getTexUV(), v.getOne());
    }

    public Vertex(double x, double y, double z, Col color) {
        this(new Point3D(x, y, z), color);
    }

    public Vertex(double x, double y, double z) {
        this(x, y, z, new Col());
    }

    public Vertex(double x, double y, double z, Point2D texUV) {
        this(new Point3D(x, y, z), new Col(), new Vec3D(0), texUV);
    }

    public Vertex(double x, double y, double z, Col color, Point2D texUV) {
        this(new Point3D(x, y, z), color, new Vec3D(), texUV);
    }

    /**
     * Special constructor for setting normal vector in the
     * solid function
     *
     * @param v
     * @param normala
     */
    public Vertex(Vertex v, Vec3D normala) {
        this(v.getPosition(), v.getColor(), normala, v.getTexUV(), v.getOne());
    }

    public Point3D getPosition() {
        return position;
    }

    public Vec3D getPositionVec() {
        return new Vec3D(position);
    }

    public Vertex mul(Mat4 m) {
        return new Vertex(new Point3D(position).mul(m), color, normal, texUV, one);
    }

    public Vertex mul(double t) {
        return new Vertex(position.mul(t), color.mul(t), normal.mul(t), texUV.mul(t), one * t);
    }

    public Optional<Vertex> dehomog() {
        if (this.getPosition().dehomog().isPresent())
            return Optional.of(this.mul(1 / this.getPosition().getW()));
        return Optional.empty();
    }

    public Vertex add(Vertex v) {
        return new Vertex(
                position.add(v.getPosition()),
                (color == null || v.getColor() == null) ? null : color.add(v.getColor()),
                normal.add(v.getNormal()),
                texUV.add(new Vec2D(v.getU(), v.getV())),
                one + v.getOne());
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
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

    public double getU() {
        return texUV.getX();
    }

    public double getV() {
        return texUV.getY();
    }

    public Vec3D getNormal() {
        return normal;
    }

    public double getOne() {
        return one;
    }

    public Point2D getTexUV() {
        return texUV;
    }

    public double getW() {
        return position.getW();
    }
}
