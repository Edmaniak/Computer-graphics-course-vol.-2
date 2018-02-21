package model.light;

import material.Material;
import model.Vertex;
import transforms.Point3D;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;

public class PointLight extends Light {


    public PointLight(Color color, Vec3D initialPosition, Vec3D pivotPoint, double intensity) {
        super(color, intensity, initialPosition, pivotPoint);
        this.intensity = Math.min(intensity, 1 / intensity);
    }

    public PointLight(Color color, Vec3D initialPosition, double intensity) {
        super(color, intensity, initialPosition);
        this.intensity = Math.min(intensity, 1 / intensity);
    }

    public PointLight(PointLight light, Vertex position) {
        super(light.color, light.intensity, new Vec3D(position.getPosition()));
    }

    public int calculateRed(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getRed() * intensity * material.getKd() * Util.dotProduct(getPosition(), normal));
    }

    public int calculateBlue(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getBlue() * intensity * material.getKd() * Util.dotProduct(getPosition(), normal));
    }

    public int calculateGreen(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getGreen() * intensity * material.getKd() * Util.dotProduct(getPosition(), normal));
    }

    public Vec3D getPointPosition() {
        return transform.getWorldPosition();
    }
}
