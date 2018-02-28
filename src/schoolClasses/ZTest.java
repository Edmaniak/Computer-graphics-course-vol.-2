package schoolClasses;

import java.awt.image.BufferedImage;
import java.util.Optional;

import transforms.Col;

public class ZTest {
	private final Zbuffer zBuffer; 
	private final ImageBuffer imgBuffer;
	
	public ZTest(BufferedImage img) {
		zBuffer = new Zbuffer(img);
		imgBuffer = new ImageBuffer(img);
	}
	
	public void test(int x, int y, double z, Col color) {
		
		Optional<Double> zVal = zBuffer.getPixel(x, y);

		if(!zVal.isPresent())
			return;

		if(z < zVal.get() && z >= 0) {
			zBuffer.setPixel(z, x, y);
			imgBuffer.setPixel(color,x,y);
		}
	}
}
