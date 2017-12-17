package renderer.raster;

import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterizerTriangle extends Rasterizer {


    public RasterizerTriangle(BufferedImage img) {
        super(img);
    }

    public void draw(Vec3D vec1, Vec3D vec2, Vec3D vec3, Color color) {
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.drawLine((int) vec1.getX(), (int) vec1.getY(), (int) vec2.getX(), (int) vec2.getY());
        g.drawLine((int) vec2.getX(), (int) vec2.getY(), (int) vec3.getX(), (int) vec3.getY());
        g.drawLine((int) vec3.getX(), (int) vec3.getY(), (int) vec1.getX(), (int) vec1.getY());
    }
}
