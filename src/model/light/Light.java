package model.light;

import model.objects.SceneObject;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public abstract class Light extends SceneObject {

    protected double intensity;
    protected Col color;


    protected Light(Col color, double intensity, Vec3D initialPosition, Vec3D pivotPoint) {
        super(pivotPoint, initialPosition);
        this.color = color;
        this.intensity = intensity;
    }

    protected Light(Col color, double intensity, Vec3D initialPosition) {
        super(initialPosition);
        this.color = color;
        this.intensity = intensity;
    }

    protected Light(Col color, double intensity) {
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

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public int getRed() {
        return (int) (intensity * color.getR());
    }

    public int getBlue() {
        return (int) (intensity * color.getB());
    }

    public int getGreen() {
        return (int) (intensity * color.getG());
    }


}
