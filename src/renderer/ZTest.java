package renderer;

import java.awt.image.BufferedImage;
import java.util.Optional;

import transforms.Col;

public class ZTest {
    private final ZBuffer2 zBuffer;
    private final ImageBuffer imgBuffer;

    public ZTest(BufferedImage img) {
        zBuffer = new ZBuffer2(img);
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

    public int getWidth() {
        return zBuffer.getWidth();
    }

    public int getHeight() {
        return zBuffer.getHeight();
    }

    public ImageBuffer getImgBuffer() {
        return imgBuffer;
    }

    public ZBuffer2 getzBuffer() {
        return zBuffer;
    }
}
