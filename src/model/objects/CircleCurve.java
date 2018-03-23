package model.objects;

import model.Part;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class CircleCurve extends Curve {

    public CircleCurve(Vec3D initialPosition, Point3D center, double radius, int step) {

        super(new Vec3D(center), initialPosition, step);

        for (int i = 0; i <= step; i++) {

            double angle = (Math.PI * 2 / step) * i;
            Point3D point = new Point3D(radius * Math.cos(angle)+center.getX(), radius * Math.sin(angle)+center.getY(), center.getZ());

            vertices().add(new Vertex(point));
            indexes().add(i);
            indexes().add(i + 1);
        }

        parts().add(new Part(Part.Type.LINE, (step * 2) - 1, 0));
    }
}
