package renderer.raster;

import app.App;
import material.Material;
import model.Vertex;
import model.light.AmbientLight;
import model.light.PointLight;
import renderer.ZBuffer;
import transforms.Vec3D;
import utilities.Util;

import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class RasterizerTriangle extends Rasterizer {

    private List<PointLight> lights;
    private AmbientLight ambientLight;

    public RasterizerTriangle(BufferedImage img, ZBuffer zb, List<PointLight> lights, AmbientLight ambientLight) {
        super();
        this.lights = lights;
        this.ambientLight = ambientLight;
    }

    public void rasterize(Vertex vec1, Vertex vec2, Vertex vec3, Material material) {
        Vertex[] vertx = sort(new Vertex[]{vec1, vec2, vec3});
        Vec3D v1 = Util.vectorize(Util.unProject(vertx[0]), Util.unProject(vertx[1]));
        Vec3D v2 = Util.vectorize(Util.unProject(vertx[0]), Util.unProject(vertx[2]));
        Vec3D normal = Util.crossProduct(v1,v2);
        normal = Util.dotProduct(App.app.getScene().getCamera().getViewVector(), normal) > 0 ? Util.mul(normal, -1) : normal;
        System.out.println(normal);
        // Top and bottom triangle
        for (int i = 0; i <= 1; i++) {
            // one of the triangles
            for (int y = (int) vertx[0 + i].getY() + 1; y <= Math.min((int) vertx[1 + i].getY(), App.HEIGHT - 1); y++) {

                // kalkulace interpolacniho k + interpolace
                double s1 = (y - vertx[0 + i].getY()) / (vertx[1 + i].getY() - vertx[0 + i].getY());
                double s2 = (y - vertx[0].getY()) / (vertx[2].getY() - vertx[0].getY());

                int x1 = Util.lerpInt(vertx[0 + i].getX(), vertx[1 + i].getX(), s1);
                int x2 = Util.lerpInt(vertx[0].getX(), vertx[2].getX(), s2);

                double z1 = Util.lerpDouble(vertx[0 + i].getZ(), vertx[1 + i].getZ(), s1);
                double z2 = Util.lerpDouble(vertx[0].getZ(), vertx[2].getZ(), s2);

                // Swaping x coordinates when we need to draw the line from the other end
                if (x2 < x1) {
                    int pom = x1;
                    x1 = x2;
                    x2 = pom;
                    double pom2 = z1;
                    z1 = z2;
                    z2 = pom2;
                }


                double keo = x1;
                // Main rasterizing cycle
                for (int x = Math.max(x1 + 1, 0); x <= Math.min(x2, App.WIDTH - 1); x++) {
                    double s3 = ((double) x - keo) / (x2 - keo);
                    double z = Util.lerpDouble(z1, z2, s3);
/*
                    double w1 = (((vertx[1].getY() - vertx[2].getY()) * (x - vertx[2].getX())) + ((vertx[2].getX() - vertx[1].getX()) * (y - vertx[2].getY()))) /
                            ((vertx[1].getY() - vertx[2].getY()) * (vertx[0].getX() - vertx[2].getX()) + (vertx[2].getX() - vertx[1].getX()) * (vertx[0].getY() - vertx[2].getY()));

                    double w2 = (((vertx[2].getY() - vertx[0].getY()) * (x - vertx[2].getX())) + ((vertx[0].getX() - vertx[2].getX()) * (y - vertx[2].getY()))) /
                            (((vertx[1].getY() - vertx[2].getY()) * (vertx[0].getX() - vertx[2].getX())) + ((vertx[2].getX() - vertx[1].getX()) * (vertx[0].getY() - vertx[2].getY())));

                    double w3 = 1 - w1 - w2;

                    Color c1 = new Color((vertx[0].getColor().getRed()), vertx[0].getColor().getGreen(), vertx[0].getColor().getBlue());
                    Color c2 = new Color((vertx[1].getColor().getRed()), vertx[1].getColor().getGreen(), vertx[1].getColor().getBlue());
                    Color c3 = new Color((vertx[2].getColor().getRed()), vertx[2].getColor().getGreen(), vertx[2].getColor().getBlue());

                    int r = (c1.getRed() + c2.getRed() + c3.getRed());
                    int g = (c1.getGreen() + c2.getGreen() + c3.getGreen());
                    int b = (c1.getBlue() + c2.getBlue() + c3.getBlue());


*/
                    int r = Math.min(ambientLight.calculateRed(material) + lights.get(0).calculateRed(material, normal, App.app.getScene().getCamera().getViewVector()), 255);
                    int g = Math.min(ambientLight.calculateGreen(material) + lights.get(0).calculateGreen(material, normal, App.app.getScene().getCamera().getViewVector()), 255);
                    int b = Math.min(ambientLight.calculateBlue(material) + lights.get(0).calculateBlue(material, normal, App.app.getScene().getCamera().getViewVector()), 255);

                    int red = r > 0 ? r : 0;
                    int blue = b > 0 ? b : 0;
                    int green = g > 0 ? g : 0;
/*
                    if (zb.getDepth(x, y) >= z && z >= 0) {

                        img.setRGB(x, y, new Color(red, green, blue).hashCode());
                        zb.setDepth(x, y, z);
                    }*/
                }
            }
        }
    }
}
