package renderer.raster;



import model.Vertex;
import renderer.ZBuffer;
import java.awt.image.BufferedImage;



public abstract class Rasterizer {

    protected final BufferedImage img;
    protected final ZBuffer zb;


    public Rasterizer(BufferedImage img, ZBuffer zb) {
        this.img = img;
        this.zb = zb;
    }

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
