package material;

import transforms.Col;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Texture {

    private int width;
    private int height;
    private BufferedImage img;

    public Texture(String path) {
        try {
            this.img = ImageIO.read(new File(path));
            this.width = img.getWidth();
            this.height = img.getHeight();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Optional<Col> getPixel(int x, int y) {

        if (x < 0 || x >= width)
            return Optional.empty();

        if (y < 0 || y >= height)
            return Optional.empty();

        return Optional.of(new Col(img.getRGB(x, y)));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
