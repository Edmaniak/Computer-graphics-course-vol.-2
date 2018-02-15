package renderer.raster;

import app.App;
import renderer.ZBuffer;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

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
            for (int y = (int) vertx[0 + i].getY() + 1; y <= Math.min(vertx[1 + i].getY(), App.HEIGHT - 1); y++) {

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
                }

                double keo = x1;
                // Main rasterizing cycle
                for (int x = x1; x < x2; x++) {
                    double s3 = (x - keo) / (x2 - keo);
                    double z = (z1 * (1 - s3) + z2 * s3);

                    int r = (int) (20 * (1 - s3) + 196 * s3);
                    int g = (int) (50 * (1 - s3) + 120 * s3);
                    int b = (int) (60 * (1 - s3) + 2 * s3);

                    if (zb.getDepth(x, y) > z && z >= 0) {
                        img.setRGB(x, y, new Color(r, g, b).hashCode());
                        zb.setDepth(x, y, z);
                    }

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
