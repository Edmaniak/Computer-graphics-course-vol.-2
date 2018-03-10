package material;

import transforms.Col;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public Col getPixel(int x, int y) {
        return new Col(img.getRGB(x, y));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
