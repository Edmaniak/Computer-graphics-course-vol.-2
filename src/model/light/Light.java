package model.light;

import model.objects.SceneObject;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public abstract class Light extends SceneObject {

    protected double intensity;
    protected Color color;


    protected Light(Color color, double intensity, Vec3D initialPosition, Vec3D pivotPoint) {
        super(pivotPoint, initialPosition);
        this.color = color;
        this.intensity = intensity;
    }

    protected Light(Color color, double intensity, Vec3D initialPosition) {
        super(initialPosition);
        this.color = color;
        this.intensity = intensity;
    }

    protected Light(Color color, double intensity) {
        super();
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
