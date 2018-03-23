package renderer.buffer;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class ZBuffer implements Raster<Double> {
    private final int width;
    private final int height;
    private final double[][] zBuffer;

    public ZBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        zBuffer = new double[width][height];
        clear();
    }

    public ZBuffer(BufferedImage img) {
        this(img.getWidth(), img.getHeight());
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Optional<Double> getPixel(int x, int y) {
        if (isPointValid(x, y))
            return Optional.of(new Double(zBuffer[x][y]));
        return Optional.empty();
    }

    @Override
    public void setPixel(Double pixel, int x, int y) {
        if (isPointValid(x, y))
            zBuffer[x][y] = pixel;
    }

    private boolean isPointValid(int x, int y) {
        return (x < width && x >= 0 && y < height && y >= 0);
    }

    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zBuffer[i][j] = 1;
            }
        }
    }

}
