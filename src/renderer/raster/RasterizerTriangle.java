package renderer.raster;

import app.App;
import renderer.ZBuffer;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class RasterizerTriangle extends Rasterizer {

    private ZBuffer zb;


    public RasterizerTriangle(BufferedImage img, ZBuffer zb) {
        super(img);
        this.zb = zb;
    }

    public void rasterize(Vec3D vec1, Vec3D vec2, Vec3D vec3) {
        Vec3D[] vertx = sort(new Vec3D[]{vec1, vec2, vec3});

        // Top and bottom triangle
        for (int i = 0; i <= 1; i++) {
            // one of the triangles
            for (int y = (int) vertx[0 + i].getY() + 1; y <= Math.min((int)vertx[1 + i].getY(), App.HEIGHT - 1); y++) {

                // kalkulace interpolacniho k + interpolace
                double s1 = (y - vertx[0 + i].getY()) / (vertx[1 + i].getY() - vertx[0 + i].getY());
                double s2 = (y - vertx[0].getY()) / (vertx[2].getY() - vertx[0].getY());

                int x1 = Util.lerpInt(vertx[0 + i].getX(), vertx[1 + i].getX(), s1);
                int x2 = Util.lerpInt(vertx[0].getX(), vertx[2].getX(), s2);

                double z1 = Util.lerpDouble(vertx[0 + i].getZ(), vertx[1 + i].getZ(), s1);
                double z2 = Util.lerpDouble(vertx[0].getZ(), vertx[2].getZ(), s2);

                // Swaping x coordinates when we need to draw the line from the other end
                if (x2 < x1) {
                    int pom = x1;
                    x1 = x2;
                    x2 = pom;
                    double pom2 = z1;
                    z1 = z2;
                    z2 = pom2;
                }


                double keo = x1;
                // Main rasterizing cycle
                for (int x = Math.max(x1 + 1, 0); x <= Math.min(x2, App.WIDTH -1); x++) {
                    double s3 = ((double) x - keo) / (x2 - keo);
                    double z = Util.lerpDouble(z1, z2, s3);

                    int r = (int) (0 * (1 - s3) + 255 * s3);
                    int g = (int) (255 * (1 - s3) + 0 * s3);
                    int b = (int) (0 * (1 - s3) + 0 * s3);

                    if (zb.getDepth(x, y) >= z && z >= 0) {
                        img.setRGB(x, y, new Color(r, g, b).hashCode());
                        zb.setDepth(x, y, z);
                    }

                   // img.setRGB(x, y, new Color(r, g, b).hashCode());

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
