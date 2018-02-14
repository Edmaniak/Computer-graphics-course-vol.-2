package renderer.raster;

import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RasterizerTriangle extends Rasterizer {

    public RasterizerTriangle(BufferedImage img) {
        super(img);
    }

    public void rasterize(Vec3D vec1, Vec3D vec2, Vec3D vec3) {
        Vec3D[] vertx = sort(new Vec3D[]{vec1, vec2, vec3});

        // Top and bottom triangle
        for (int i = 0; i <= 1; i++) {
            // one of the triangles
            for (int y = (int) vertx[0 + i].getY() + 1; y < vertx[1 + i].getY(); y++) {

                // kalkulace interpolacniho k + interpolace
                double s1 = (y - (int) vertx[0 + i].getY()) / (vertx[1 + i].getY() - (int) vertx[0 + i].getY());
                double s2 = (y - (int) vertx[0].getY()) / (vertx[2].getY() - (int) vertx[0].getY());

                int x1 = (int) (vertx[0 + i].getX() * (1 - s1) + vertx[1 + i].getX() * s1);
                int x2 = (int) (vertx[0].getX() * (1 - s2) + vertx[2].getX() * s2);


                // Swaping x coordinates when we need to draw the line from the other end
                if (x2 < x1) {
                    int pom = x1;
                    x1 = x2;
                    x2 = pom;
                }

                double keo = x1;
                // Main rasterizing cycle
                for (int x = x1; x < x2; x++) {
                    double colorPar = (x - keo) / (x2 - keo);
                    int r = (int) (20 * (1 - colorPar) + 196 * colorPar);
                    int g = (int) (50 * (1 - colorPar) + 120 * colorPar);
                    int b = (int) (60 * (1 - colorPar) + 2 * colorPar);
                    img.setRGB(x, y, new Color(r, g, b).hashCode());
                }
            }
        }
    }

    private Vec3D[] sort(Vec3D[] array) {

        if (array[0].getY() > array[1].getY()) {
            Vec3D pom = array[1];
            array[1] = array[0];
            array[0] = pom;
        }

        if (array[0].getY() > array[2].getY()) {
            Vec3D pom = array[2];
            array[2] = array[0];
            array[0] = pom;
        }

        if (array[1].getY() > array[2].getY()) {
            Vec3D pom = array[2];
            array[2] = array[1];
            array[1] = pom;
        }

        return array;

    }


}
