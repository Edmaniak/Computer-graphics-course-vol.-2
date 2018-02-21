package schoolClasses;

import java.util.Optional;

public interface Raster<PixelType> {
	
	public int getWidth();
	public int getHeight();
	public Optional<PixelType> getPixel(int x, int y);
	public void setPixel(PixelType pixel, int x, int y);
	
}
