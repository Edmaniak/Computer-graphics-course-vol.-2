package renderer.raster;

import java.awt.*;
import java.awt.image.BufferedImage;

import app.App;
import renderer.ZBuffer;
import transforms.Vec3D;
import utilities.Util;

public class RasterizerLine extends Rasterizer {

    public RasterizerLine(BufferedImage img, ZBuffer zb) {
        super(img, zb);
    }

    public void rasterize(Vec3D vec1, Vec3D vec2) {
        Vec3D[] vertx = sort(new Vec3D[]{vec1, vec2});

        // Top and bottom triangle
        // one of the triangles
        for (int y = (int) vertx[0].getY() + 1; y <= Math.min(Math.floor(vertx[1].getY()), App.HEIGHT - 1); y++) {

            // kalkulace interpolacniho k + interpolace
            double s1 = (y - vertx[0].getY()) / (vertx[1].getY() - vertx[0].getY());

            int x = Util.lerpInt(vertx[0].getX(), vertx[1].getX(), s1);
            double z = Util.lerpDouble(vertx[0].getZ(), vertx[1].getZ(), s1);

            // Main rasterizing cycle

            if (zb.getDepth(x, y) >= z && z >= 0) {
                img.setRGB(x, y, Color.BLACK.hashCode());
                zb.setDepth(x, y, z);
            }


        }
    }



    @Override
    protected Vec3D[] sort(Vec3D[] array) {

        if (array[0].getY() > array[1].getY()) {
            Vec3D pom = array[1];
            array[1] = array[0];
            array[0] = pom;
        }

        return array;

    }
}
