package schoolClasses;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class Zbuffer implements Raster<Double> {
	private final int width;
	private final int height;
	private final double[][] zBuffer;

	public Zbuffer(int width, int height) {
		this.width = width;
		this.height = height;
		zBuffer = new double[width][height];
	}

	public Zbuffer(BufferedImage img) {
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
		if (isPointValid(x,y)) {
			return Optional.of(new Double(zBuffer[x][y]));
		}
		return Optional.empty();
	}

	@Override
	public void setPixel(Double pixel, int x, int y) {
		
	}

	private boolean isPointValid(int x, int y) {
		return (x < width && x >= 0 && y < height && y >= 0);
	}

}
