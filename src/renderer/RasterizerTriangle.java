package renderer;

import transforms.Vec3D;

import java.awt.image.BufferedImage;

public class RasterizerTriangle extends Rasterizer {


    public RasterizerTriangle(BufferedImage img) {
        super(img);
    }

    public void draw(Vec3D vec1, Vec3D vec2, Vec3D vec3) {
        img.getGraphics().drawLine((int) vec1.getX(), (int) vec1.getY(), (int) vec2.getX(), (int) vec2.getY());
        img.getGraphics().drawLine((int) vec2.getX(), (int) vec2.getY(), (int) vec3.getX(), (int) vec3.getY());
        img.getGraphics().drawLine((int) vec3.getX(), (int) vec3.getY(), (int) vec1.getX(), (int) vec1.getY());

        System.out.println(vec1 + " " + vec2 + " " + vec3);
    }
}
