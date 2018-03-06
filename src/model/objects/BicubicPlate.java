package model.objects;

import model.Part;
import model.Vertex;
import transforms.Bicubic;
import transforms.Cubic;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class BicubicPlate extends Solid {

    public BicubicPlate(Color color, Vec3D initialPosition, int step) {
        super(color, initialPosition);


        Point3D points[] = new Point3D[]{
                new Point3D(0, 0, 0),
                new Point3D(0.5, 0, 0),
                new Point3D(1, 0, 0),
                new Point3D(1.5, 0, 0),
                new Point3D(0, 0, 0.5),
                new Point3D(0.5, 1, 0.5),
                new Point3D(1, 1, 0.5),
                new Point3D(1.5, 0, 0.5),
                new Point3D(0, 1, 1),
                new Point3D(0.5, 1, 1),
                new Point3D(1, 0, 1),
                new Point3D(1.5, 0, 1),
                new Point3D(0, 0, 1.5),
                new Point3D(0.5, 0, 1.5),
                new Point3D(1, 0, 1.5),
                new Point3D(1.5, 0, 1.5),
        };


        Bicubic cubic = new Bicubic(Cubic.BEZIER, points);

        for (int i = 0; i <= step; i++) {
            for (int j = 0; j <= step; i++) {
                double mark = i * (1 / (double) step);
                double mark2 = j * (1 / (double) step);
                vertices().add(new Vertex(cubic.compute(mark, mark2)));
                indexes().add(i);
                indexes().add(i + 1);
            }
        }

        parts().add(new Part(Part.Type.LINE, (step * 2) - 1, 0));
    }
}
