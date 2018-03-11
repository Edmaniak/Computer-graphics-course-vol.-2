package model.light;

import material.Material;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;

public class PointLight extends Light {


    public PointLight(Col color, Vec3D initialPosition, Vec3D pivotPoint, double intensity) {
        super(color, intensity, initialPosition, pivotPoint);
        this.intensity = Math.min(intensity, 1 / intensity);
    }

    public PointLight(Col color, Vec3D initialPosition, double intensity) {
        super(color, intensity, initialPosition);
        this.intensity = Math.min(intensity, 1 / intensity);
    }

    public PointLight(PointLight light, Vertex position) {
        super(light.color, light.intensity, new Vec3D(position.getPosition()));
    }

    public int calculateRed(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getR() * intensity * material.getKd() * Util.dotProduct(getPosition(), normal));
    }

    public int calculateBlue(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getB() * intensity * material.getKd() * Util.dotProduct(getPosition(), normal));
    }

    public int calculateGreen(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getG() * intensity * material.getKd() * Util.dotProduct(getPosition(), normal));
    }

    public Vec3D getPointPosition() {
        return transform.getWorldPosition();
    }
}
