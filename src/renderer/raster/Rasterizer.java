package renderer.raster;



import model.Vertex;
import renderer.ImageBuffer;
import renderer.ZBuffer;
import java.awt.image.BufferedImage;



public abstract class Rasterizer {





    protected Vertex[] sort(Vertex[] array) {

        if (array[0].getY() > array[1].getY()) {
            Vertex pom = array[1];
            array[1] = array[0];
            array[0] = pom;
        }

        if (array[0].getY() > array[2].getY()) {
            Vertex pom = array[2];
            array[2] = array[0];
            array[0] = pom;
        }

        if (array[1].getY() > array[2].getY()) {
            Vertex pom = array[2];
            array[2] = array[1];
            array[1] = pom;
        }

        return array;

    }

}
