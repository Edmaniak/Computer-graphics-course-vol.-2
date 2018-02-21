package model.light;

import material.Material;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class AmbientLight extends Light {

    public AmbientLight(Color color, double intensity) {
        super(color, intensity);
    }

    public int calculateRed(Material material) {
        return (int) (material.getIluminatedRed() * intensity);
    }

    public int calculateBlue(Material material) {
        return (int) (material.getIluminatedBlue() * intensity);
    }

    public int calculateGreen(Material material) {
        return (int) (material.getIluminatedGreen() * intensity);
    }

}
