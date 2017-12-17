package model.objects;

import model.Part;
import model.Vertex;
import transforms.Cubic;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class BezierCurve extends Curve {

    public BezierCurve(Color color, Vec3D initialPosition, Point3D p1, Point3D p2, Point3D p3, Point3D p4, int step) {
        super(color, new Vec3D(0, 0, 0), initialPosition, step);

        Cubic cubic = new Cubic(Cubic.BEZIER, p1, p2, p3, p4);

        for (int i = 0; i <= step; i++) {
            double mark = i * (1 / (double) step);
            vertices().add(new Vertex(cubic.compute(mark)));
            indexes().add(i);
            indexes().add(i + 1);
        }

        parts().add(new Part(Part.Type.LINE, (step * 2) - 1, 0));
    }
}
