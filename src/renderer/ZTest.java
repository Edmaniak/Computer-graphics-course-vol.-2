package renderer;

import java.awt.image.BufferedImage;
import java.util.Optional;

import transforms.Col;

public class ZTest {
    private final ZBuffer zBuffer;
    private final ImageBuffer imgBuffer;

    public ZTest(BufferedImage img) {
        zBuffer = new ZBuffer(img);
        imgBuffer = new ImageBuffer(img);
    }

    public void test(int x, int y, double z, Col color) {

        Optional<Double> zVal = zBuffer.getPixel(x, y);

        if (!zVal.isPresent())
            return;

        if (z < zVal.get() && z >= 0) {
            zBuffer.setPixel(z, x, y);
            imgBuffer.setPixel(color, x, y);
        }
    }

    public void test(double x, double y, double z, Col color) {
        test((int) x, (int) y, (int) z, color);
    }

    public int getWidth() {
        return zBuffer.getWidth();
    }

    public int getHeight() {
        return zBuffer.getHeight();
    }

    public ImageBuffer getImgBuffer() {
        return imgBuffer;
    }

    public ZBuffer getzBuffer() {
        return zBuffer;
    }
}
