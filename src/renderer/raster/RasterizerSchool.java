package renderer.raster;

import model.Vertex;
import renderer.ZTest;
import transforms.Col;
import transforms.Vec3D;

import java.util.function.Function;

public class RasterizerSchool {

    private final ZTest zTest;
    //private final Function<Vertex, Col> shader;

    public RasterizerSchool(ZTest zTest) {
        this.zTest = zTest;
    }

    public void rasterize(Vertex v1, Vertex v2, Vertex v3) {

        if (!v1.getPosition().dehomog().isPresent())
            return;

        if (!v2.getPosition().dehomog().isPresent())
            return;

        if (!v3.getPosition().dehomog().isPresent())
            return;

        // vertexy pro vypocet barvy
        Vertex vA = v1;
        Vertex vB = v2;
        Vertex vC = v3;

        // Sorting
        if(vA.getY() > vB.getY()) {
            Vertex pom = vB;
            vB = vA;
            vA = pom;
        }

        if(vA.getY() > vC.getY()) {
            Vertex pom = vC;
            vC = vA;
            vA = pom;
        }

        if(vB.getY() > vC.getY()) {
            Vertex pom = vC;
            vC = vB;
            vB = pom;
        }

        // Dehomogeniace
        Vec3D a = project2D(v1.getPosition().dehomog().get());
        Vec3D b = project2D(v2.getPosition().dehomog().get());
        Vec3D c = project2D(v3.getPosition().dehomog().get());

        for (int y = (int) a.getY() + 1; y <= c.getY(); y++) {
            // TODO HLIDAT MEZE OBRAZOVKY
            if(y > zTest.getHeight())
                return;

            double s1 = (y - a.getY()) / (b.getY() - a.getY());
            double s2 = (y - a.getY()) / (c.getY() - a.getY());

            double x1 = a.getX() * (1 - s1) + a.getX() * s1;
            double x2 = a.getX() * (1 - s2) + c.getX() * s2;

            Vec3D vAB = a.mul(1 - s1).add(b.mul(s1));
            Vec3D vAC = a.mul(1 - s2).add(c.mul(s2));

            Vertex vertexAB = vA.mul(1-s1).add(vB.mul(s1));
            Vertex vertexAC = vA.mul(1-s2).add(vC.mul(s2));

            // Swaping x,z coordinates when we need to draw the line from the other end
            if (vAC.getX() < vAB.getX()) {
                Vec3D pom = vAC;
                vAC = vAB;
                vAB = pom;
            }

            for (int x = (int) vAB.getX(); x < vAC.getX(); x++) {
                double s3 = (x - x1) / (x2 - x1);
                double z = vAB.getZ() * (1 - s3) + vAC.getZ() * s3;
                Vertex vertexABC = vertexAB.mul(1-s3).add(vertexAC.mul(s3));
                //zTest.test(x, y, z, shader.apply(vertexABC));

            }


        }

    }

    private Vec3D project2D(Vec3D v) {
        double x = (v.getX() + 1) * zTest.getWidth() / 2;
        double y = (-v.getY() + 1) * zTest.getHeight() / 2;
        return new Vec3D(x, y, v.getZ());
    }

}
