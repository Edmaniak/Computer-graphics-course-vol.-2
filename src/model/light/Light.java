package model.light;

import material.Material;
import model.Vertex;
import model.objects.SceneObject;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;
import utilities.Util;

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

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public double getIntensity() {
        return intensity;
    }
}
