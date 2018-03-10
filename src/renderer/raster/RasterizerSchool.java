package renderer.raster;

import model.Vertex;
import renderer.ZTest;
import transforms.Col;
import transforms.Vec3D;

import java.util.function.Function;

public class RasterizerSchool {

    private final ZTest zTest;
    private final Function<Vertex, Col> shader;

    public RasterizerSchool(ZTest zTest, Function<Vertex, Col> shader) {
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

        // Sorting
        if (v1.getY() < v2.getY()) {
            Vertex pom = v2;
            v2 = v1;
            vB = vA;
            v1 = pom;
            vA = pom;
        }

        if (v1.getY() < v3.getY()) {
            Vertex pom = v3;
            v3 = v1;
            vC = vA;
            v1 = pom;
            vA = pom;
        }

        if (v2.getY() < v3.getY()) {
            Vertex pom = v3;
            v3 = v2;
            vC = vB;
            v2 = pom;
            vB = pom;
        }

        // Dehomogeniace
        Vec3D a = project2D(v1.getPosition().dehomog().get());
        Vec3D b = project2D(v2.getPosition().dehomog().get());
        Vec3D c = project2D(v3.getPosition().dehomog().get());


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

    private void rasterizeTriangle() {

    }

    private Vec3D project2D(Vec3D v) {
        double x = (v.getX() + 1) * zTest.getWidth() / 2;
        double y = (-v.getY() + 1) * zTest.getHeight() / 2;
        return new Vec3D(x, y, v.getZ());
    }

}
