package renderer;


import java.awt.image.BufferedImage;


public abstract class Rasterizer {

    protected BufferedImage img;

    public Rasterizer(BufferedImage img) {
        this.img = img;
    }


}
