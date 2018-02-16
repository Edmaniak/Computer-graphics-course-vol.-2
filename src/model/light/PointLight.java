package model.light;

import transforms.Point3D;

import java.awt.*;

public class PointLight {

    private Point3D position;
    private double intensity;
    private Color color;

    public PointLight(Point3D position, double intensity) {
        this.intensity = Math.min(intensity, 1 / intensity);
        this.position = position;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
