package model.light;

import material.Material;
import transforms.Point3D;

import java.awt.*;

public abstract class Light {

    protected double intensity;
    protected Color color;

    public Light(Color color, double intensity) {
        this.color = color;
        this.intensity = intensity;
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

    public int getRed() {
        return (int) (intensity * color.getRed());
    }

    public int getBlue() {
        return (int) (intensity * color.getRed());
    }

    public int getGreen() {
        return (int) (intensity * color.getRed());
    }


}
