package renderer.raster;


import java.awt.image.BufferedImage;


public abstract class Rasterizer {

    protected final BufferedImage img;

    public Rasterizer(BufferedImage img) {
        this.img = img;
    }

}
