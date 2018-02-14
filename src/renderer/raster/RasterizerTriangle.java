package renderer.raster;

import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RasterizerTriangle extends Rasterizer {

    public RasterizerTriangle(BufferedImage img) {
        super(img);
    }

    public void draw(Vec3D vec1, Vec3D vec2, Vec3D vec3, Color color) {
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.drawLine((int) vec1.getX(), (int) vec1.getY(), (int) vec2.getX(), (int) vec2.getY());
        g.drawLine((int) vec2.getX(), (int) vec2.getY(), (int) vec3.getX(), (int) vec3.getY());
        g.drawLine((int) vec3.getX(), (int) vec3.getY(), (int) vec1.getX(), (int) vec1.getY());
    }

    public void rasterize(Vec3D vec1, Vec3D vec2, Vec3D vec3) {
        Vec3D[] vertx = sort(vec1, vec2, vec3);
        Graphics g = img.getGraphics();

        // Top and bottom triangle
        for (int i = 0; i <= 1; i++) {
            for (int y = (int) vertx[0 + i].getY(); y < vertx[1 + i].getY(); y++) {
                double s1 = (y - (int) vertx[0 + i].getY()) / (vertx[1 + i].getY() - (int) vertx[0 + i].getY());
                double s2 = (y - (int) vertx[0].getY()) / (vertx[2].getY() - (int) vertx[0].getY());
                int x1 = (int) (vertx[0 + i].getX() * (1 - s1) + vertx[1 + i].getX() * s1);
                int x2 = (int) (vertx[0].getX() * (1 - s2) + vertx[2].getX() * s2);

                if (x2 < x1) {
                    int pom = x1;
                    x1 = x2;
                    x2 = pom;
                }

                for (int x = x1; x < x2; x++) {
                    img.setRGB(x, y, Color.WHITE.hashCode());
                }

            }
        }


    }

    private Vec3D[] sort(Vec3D vec1, Vec3D vec2, Vec3D vec3) {
        Vec3D[] array = new Vec3D[3];

        array[0] = new Vec3D(vec1);
        array[1] = new Vec3D(vec2);
        array[2] = new Vec3D(vec3);

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
