package model.light;

import material.Material;
import transforms.Point3D;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;

public class PointLight extends Light {

    private Vec3D position;


    public PointLight(Color color, Vec3D position, double intensity) {
        super(color, intensity);
        this.intensity = Math.min(intensity, 1 / intensity);
        this.position = position;
    }

    public Vec3D getPosition() {
        return position;
    }

    public void setPosition(Vec3D position) {
        this.position = position;
    }

    public int calculateRed(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getRed() * intensity * material.getKd() * Util.dotProduct(position, normal));
    }

    public int calculateBlue(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getBlue() * intensity * material.getKd() * Util.dotProduct(position, normal));
    }

    public int calculateGreen(Material material, Vec3D normal, Vec3D viewVector) {
        return (int) (color.getGreen() * intensity * material.getKd() * Util.dotProduct(position, normal));
    }


}
