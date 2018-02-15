package renderer.raster;


import app.App;

import java.awt.image.BufferedImage;
import java.util.Arrays;


public abstract class Rasterizer {

    protected final BufferedImage img;


    public Rasterizer(BufferedImage img) {
        this.img = img;
    }

}
