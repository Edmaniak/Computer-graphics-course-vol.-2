package model.objects;

import material.Material;
import model.Part;
import model.Vertex;
import transforms.*;

import java.awt.*;

public class BicubicPlate extends Solid {

    public BicubicPlate(Vec3D initialPosition, int step) {
        super(initialPosition);


        Point3D points[] = new Point3D[]{
                new Point3D(-4, 0, 0),
                new Point3D(-1, 0, 0),
                new Point3D(3, 0, 0),
                new Point3D(6, 0, 0),
                new Point3D(-4, -0.4, 2),
                new Point3D(-1, 1, 2),
                new Point3D(3, 0.5, 2),
                new Point3D(6, 0, 2),
                new Point3D(-4, 0, 3),
                new Point3D(-1, 0, 3),
                new Point3D(3, 0.5, 3),
                new Point3D(6, 0, 3),
                new Point3D(-4, 0, 7),
                new Point3D(-1, 0, 7),
                new Point3D(3, 0, 7),
                new Point3D(6, 0, 7),
        };


        Bicubic cubic = new Bicubic(Cubic.BEZIER, points);

        for (int i = 0; i < step; i++) {
            for (int j = 0; j < step; j++) {
                double mark = i * (1 / (double) step);
                double mark2 = j * (1 / (double) step);
                vertices().add(new Vertex(cubic.compute(mark, mark2)));
            }
        }

        for (int i = 0; i < step - 1; i++) {
            for (int j = 0; j < step - 1; j++) {

                //spodni trojuhelnici
                indexes().add((i * step) + j);
                indexes().add((i * step) + (j + 1));
                indexes().add((i + 1) * step + j);

                //vrchni trojuhelnici
                indexes().add((i * step) + (j + 1));
                indexes().add((i + 1) * step + j);
                indexes().add((i + 1) * step + (j+1));
            }
        }

        randomizeColors(50,100,1);

        parts().add(new Part(Part.Type.TRIANGLE, (int) Math.pow(step - 1, 2) * 6, 0));
    }

    public BicubicPlate(Material material, Vec3D initialPosition, int step) {
        this(initialPosition,step);
        setMaterial(material);
    }
}
