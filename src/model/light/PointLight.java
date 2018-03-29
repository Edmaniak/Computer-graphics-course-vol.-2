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

    public PointLight(PointLight light, Vec3D position) {
        super(light.color, light.intensity, position);
    }

    public Vec3D getPointPosition() {
        //System.out.println(transform.getWorldPosition());
        return transform.getWorldPosition();
    }

    public Col getContribution(Material material, Vertex v) {
        double kd = material.getKd();
        //skalarni soucin normaloveho uhlu a uhlu dopadu
        Vec3D l = getPointPosition().sub(v.getPositionVec().mul(1 / v.getOne())).normalized().get();
        Vec3D n = v.getNormal().mul(1 / v.getOne()).normalized().get();
        // Dot product 1 * n
        double ln = Util.dotProduct(l, n);

        Col clr = color.mulna(intensity * ln * kd);

        return clr;
    }
}
