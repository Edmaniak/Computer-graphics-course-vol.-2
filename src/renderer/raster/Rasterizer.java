package renderer.raster;


import app.App;
import renderer.ZBuffer;
import transforms.Vec3D;

import java.awt.image.BufferedImage;
import java.util.Arrays;


public abstract class Rasterizer {

    protected final BufferedImage img;
    protected final ZBuffer zb;


    public Rasterizer(BufferedImage img, ZBuffer zb) {
        this.img = img;
        this.zb = zb;
    }

    protected Vec3D[] sort(Vec3D[] array) {

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
