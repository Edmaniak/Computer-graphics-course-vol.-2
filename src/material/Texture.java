package material;

import transforms.Col;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Texture {

    private BufferedImage img;

    public Texture(String path) {
        try {
            this.img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Optional<Col> getPixel(int x, int y) {

        if (x < 0 || x >= img.getWidth())
            return Optional.empty();

        if (y < 0 || y >= img.getHeight())
            return Optional.empty();

        return Optional.of(new Col(img.getRGB(x, y)));
    }
}

