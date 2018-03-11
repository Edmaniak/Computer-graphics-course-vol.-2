package renderer.raster;

import material.Material;
import model.Vertex;
import renderer.ZTest;
import renderer.shader.Shader;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class RasterizerSchool {

    private final ZTest zTest;
    private Function<Vertex, Col> shader;

    public RasterizerSchool(ZTest zTest, Shader shader) {
        this.zTest = zTest;
        this.shader = shader;
    }

    public void rasterize(Vertex v1, Vertex v2, Vertex v3) {

        if (!v1.dehomog().isPresent())
            return;

        if (!v2.dehomog().isPresent())
            return;

        if (!v3.dehomog().isPresent())
            return;

        // vertexy pro vypocet barvy
        Vertex vA = v1;
        Vertex vB = v2;
        Vertex vC = v3;

        Vec3D a = project2D(v1.getPosition().dehomog().get());
        Vec3D b = project2D(v2.getPosition().dehomog().get());
        Vec3D c = project2D(v3.getPosition().dehomog().get());

        // Sorting
        if (a.getY() > b.getY()) {
            Vec3D pom = a;
            a = b;
            b = pom;

            Vertex pom2 = vA;
            vA = vB;
            vB = pom2;
        }

        if (b.getY() > c.getY()) {
            Vec3D pom = b;
            b = c;
            c = pom;


            Vertex pom2 = vB;
            vB = vC;
            vC = pom2;
        }

        if (a.getY() > b.getY()) {
            Vec3D pom = a;
            a = b;
            b = pom;

            Vertex pom2 = vA;
            vA = vB;
            vB = pom2;
        }



        // Rasterizing triangle ABD
        for (int y = (int) a.getY() + 1; y <= b.getY(); y++) {

            if (y > zTest.getHeight() || y < 0)
                continue;

            double s1 = (y - a.getY()) / (b.getY() - a.getY());
            double s2 = (y - a.getY()) / (c.getY() - a.getY());

            Vec3D vAB = a.mul(1 - s1).add(b.mul(s1));
            Vec3D vAC = a.mul(1 - s2).add(c.mul(s2));

            Vertex vertexAB = vA.mul(1 - s1).add(vB.mul(s1));
            Vertex vertexAC = vA.mul(1 - s2).add(vC.mul(s2));

            // Swaping x,z coordinates when we need to draw the line from the other end
            if (vAC.getX() < vAB.getX()) {
                Vec3D pom1 = vAC;
                Vertex pom2 = vertexAC;
                vAC = vAB;
                vAB = pom1;
                vertexAC = vertexAB;
                vertexAB = pom2;
            }

            for (int x = (int) vAB.getX() + 1; x <= vAC.getX(); x++) {

                if (x > zTest.getWidth() || x < 0)
                    continue;

                double s3 = (x - vAB.getX()) / (vAC.getX() - vAB.getX());
                double z = vAB.getZ() * (1 - s3) + vAC.getZ() * s3;
                Vertex vertexABC = vertexAB.mul(1 - s3).add(vertexAC.mul(s3));
                zTest.test(x, y, z, shader.apply(vertexABC));
            }
        }

        // Rasterizing triangle BCD
        for (int y = (int) b.getY() + 1; y <= c.getY(); y++) {

            if (y > zTest.getHeight() || y < 0)
                continue;

            double s1 = (y - b.getY()) / (c.getY() - b.getY());
            double s2 = (y - a.getY()) / (c.getY() - a.getY());

            Vec3D vBC = b.mul(1 - s1).add(c.mul(s1));
            Vec3D vAC = a.mul(1 - s2).add(c.mul(s2));

            Vertex vertexBC = vB.mul(1 - s1).add(vC.mul(s1));
            Vertex vertexAC = vA.mul(1 - s2).add(vC.mul(s2));

            // Swaping x,z coordinates when we need to draw the line from the other end
            if (vAC.getX() < vBC.getX()) {
                Vec3D pom1 = vAC;
                Vertex pom2 = vertexAC;
                vAC = vBC;
                vBC = pom1;
                vertexAC = vertexBC;
                vertexBC = pom2;
            }

            for (int x = (int) vBC.getX() + 1; x <= vAC.getX(); x++) {

                if (x > zTest.getWidth() || x < 0)
                    continue;

                double s3 = (x - vBC.getX()) / (vAC.getX() - vBC.getX());
                double z = vBC.getZ() * (1 - s3) + vAC.getZ() * s3;
                Vertex vertexABC = vertexBC.mul(1 - s3).add(vertexAC.mul(s3));

                zTest.test(x, y, z, shader.apply(vertexABC));
            }
        }

    }

    public void rasterize(Vertex v1, Vertex v2) {

        if (!v1.dehomog().isPresent())
            return;

        if (!v2.dehomog().isPresent())
            return;

        if (v1.getY() < v2.getY()) {
            Vertex pom = v1;
            v1 = v2;
            v2 = pom;
        }

        Vertex vA = v1;
        Vertex vB = v2;

        Vec3D a = project2D(v1.getPosition().dehomog().get());
        Vec3D b = project2D(v2.getPosition().dehomog().get());

        if (b.getX() < a.getX()) {
            Vec3D pom1 = b;
            Vertex pom2 = vB;
            b = a;
            a = pom1;
            vB = vA;
            vA = pom2;
        }

        for (int y = (int) a.getY(); y <= b.getY(); y++) {

            double s1 = (y - a.getY()) / (b.getY() - a.getY());

            Vec3D vAB = a.mul(1 - s1).add(b.mul(s1));
            Vertex vertexAB = vA.mul(1 - s1).add(vB.mul(s1));

            zTest.test(vAB.getX(), y, vAB.getZ(), shader.apply(vertexAB));
        }

    }

    public void rasterizeWire(Vertex v1, Vertex v2, Vertex v3, Material material) {

        if (!v1.dehomog().isPresent())
            return;

        if (!v2.dehomog().isPresent())
            return;

        if (!v3.dehomog().isPresent())
            return;

        Vec3D a = project2D(v1.getPosition().dehomog().get());
        Vec3D b = project2D(v2.getPosition().dehomog().get());
        Vec3D c = project2D(v3.getPosition().dehomog().get());

        BufferedImage img = zTest.getImgBuffer().getImg();

        Graphics g = img.getGraphics();
        g.setColor(new Color(material.getColor().getRGB()));
        g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        g.drawLine((int) b.getX(), (int) b.getY(), (int) c.getX(), (int) c.getY());
        g.drawLine((int) c.getX(), (int) c.getY(), (int) a.getX(), (int) a.getY());


    }

    private Vec3D project2D(Vec3D v) {
        double x = (v.getX() + 1) * zTest.getWidth() / 2;
        double y = (-v.getY() + 1) * zTest.getHeight() / 2;
        return new Vec3D(x, y, v.getZ());
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
