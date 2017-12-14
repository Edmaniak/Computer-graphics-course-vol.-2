package model;

import model.objects.Curve;
import transforms.Cubic;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class BezierCurve extends Curve {

    private int step = 10;

    public BezierCurve(Color color, Vec3D initialPosition, Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        super(color, new Vec3D(0, 0, 0), initialPosition);
        Cubic cubic = new Cubic(Cubic.BEZIER, p1, p2, p3, p4);
        for (double i = 0; i < step; i++) {
            double mark = 1 / (double) step;
            vertices().add(new Vertex(cubic.compute(mark)));
            System.out.println(cubic.compute(mark));
        }
        for (int i = 0; i < vertices().size() - 1; i++) {
            indexes().add(i);
            indexes().add(i + 1);
        }
        getParts().add(new Part(Part.Type.LINE, (int) step * 2, 0));
    }
}
