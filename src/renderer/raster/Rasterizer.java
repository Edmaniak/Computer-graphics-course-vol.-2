package renderer.raster;


import app.App;

import java.awt.image.BufferedImage;
import java.util.Arrays;


public abstract class Rasterizer {

    protected final BufferedImage img;
    protected final int[][] zBuffer;

    public Rasterizer(BufferedImage img) {
        this.img = img;
        zBuffer = new int[App.WIDTH][App.HEIGHT];

        // Initilazing z-buffer to 1
        for (int[] row : zBuffer)
            Arrays.fill(row, 1);

    }

}
