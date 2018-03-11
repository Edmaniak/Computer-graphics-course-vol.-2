package model.light;

import material.Material;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class AmbientLight extends Light {

    public AmbientLight(Col color, double intensity) {
        super(color, intensity);
    }

    public Col getContribution(Material m) {
        return color.mulna(intensity).mulna(m.getKa());
    }

}
