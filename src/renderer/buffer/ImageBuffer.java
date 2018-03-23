package renderer.buffer;


import transforms.Col;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageBuffer implements Raster<Col> {
    private final int width;
    private final int height;
    private final BufferedImage img;

    public ImageBuffer(BufferedImage img) {
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.img = img;
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
    public Optional<Col> getPixel(int x, int y) {
        if (isPointValid(x, y))
            return Optional.of(new Col(img.getRGB(x, y)));
        return Optional.empty();
    }

    @Override
    public void setPixel(Col pixel, int x, int y) {
        if (isPointValid(x, y))
            img.setRGB(x, y, pixel.getRGB());

    }

    private boolean isPointValid(int x, int y) {
        return (x < width && x >= 0 && y < height && y >= 0);
    }

    public void clear() {

    }

    public BufferedImage getImg() {
        return img;
    }
}
