package schoolClasses;

import java.util.Optional;

public interface Raster<PixelType> {
	
	int getWidth();
	int getHeight();
	Optional<PixelType> getPixel(int x, int y);
	void setPixel(PixelType pixel, int x, int y);
	void clear();

	
}
