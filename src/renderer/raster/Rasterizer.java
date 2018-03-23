package renderer.raster;

import model.Vertex;
import renderer.ZTest;
import renderer.shader.Shader;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class Rasterizer {

    private final ZTest zTest;
    private Function<Vertex, Col> shader;

    public Rasterizer(ZTest zTest, Shader shader) {
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
        Vertex vA = v1.mul(1/v1.getPosition().getW());
        Vertex vB = v2.mul(1/v2.getPosition().getW());
        Vertex vC = v3.mul(1/v3.getPosition().getW());

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

        if (v1.getY() > v2.getY()) {
            Vertex pom = v1;
            v1 = v2;
            v2 = pom;
        }

        Vertex vA = v1.mul(1/v1.getPosition().getW());
        Vertex vB = v2.mul(1/v2.getPosition().getW());

        Vec3D o = project2D(v1.getPosition().dehomog().get());
        Vec3D e = project2D(v2.getPosition().dehomog().get());

        double dx = e.getX() - o.getX();
        double dy = e.getY() - o.getY();

        if (Math.abs(dy) <= Math.abs(dx)) {
            // one point line
            if ((o.getX() == e.getX()) && (o.getY() == e.getY())) {
                zTest.test(o.getX(), o.getY(), o.getZ(), new Col(255, 0, 0));
            } else if (e.getX() < o.getX()) {
                Vec3D pom = o;
                o = e;
                e = pom;
            }

            double k = dy / dx;
            double fy = o.getY();

            for (int x = (int) o.getX(); x <= e.getX(); x++) {
                int y = (int) fy;
                double t = (x - o.getX()) / (e.getX() - o.getX());
                double z = o.getZ() * (1 - t) + e.getZ() * t;
                Vertex v = vA.mul(1-t).add(vB.mul(t));
                zTest.test(x, y, z, shader.apply(v));
                fy += k;
            }

        } else {
            if (e.getY() < o.getY()) {
                Vec3D pom = o;
                o = e;
                e = pom;
            }

            double k = dx / dy;
            double fx = o.getX();

            for (int y = (int) o.getY(); y <= e.getY(); y++) {
                int x = (int) fx;
                double t = (y - o.getY()) / (e.getY() - o.getY());
                double z = o.getZ() * (1 - t) + e.getZ() * t;
                Vertex v = vA.mul(1-t).add(vB.mul(t));
                zTest.test(x, y, z, shader.apply(v));
                fx += k;
            }
        }

    }

    public void rasterizeWire(Vertex v1, Vertex v2, Vertex v3) {
        rasterize(v1,v2);
        rasterize(v1,v3);
        rasterize(v2,v3);
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
