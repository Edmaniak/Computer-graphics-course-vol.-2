package schoolClasses;

import app.App;
import model.Vertex;
import transforms.Col;
import transforms.Vec3D;

import java.util.function.Function;

public class RasterizerSchool {

    private final ZTest zTest;
    private final Function<Vertex, Col> shader;

    public RasterizerSchool(ZTest zTest, Function<Vertex, Col> shader) {
        this.zTest = zTest;
        this.shader =
    }

    /**
     * Souradnice nikoliv v obrazovce pred dehomogenizaci
     *
     * @param v1
     * @param v2
     * @param v3
     */
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

        // searadit
        Vec3D a = project2D(v1.getPosition().dehomog().get());
        Vec3D b = project2D(v1.getPosition().dehomog().get());
        Vec3D c = project2D(v1.getPosition().dehomog().get());

        for (int y = (int) a.getY() + 1; y <= c.getY(); y++) {
            // TODO HLIDAT MEZE OBRAZOVKY

            double s1 = (y - a.getY()) / (b.getY() - a.getY());
            double x1 = a.getX() * (1 - s1) + a.getX() * s1;
            double x2 = 0;

            Vec3D vAB = a.mul(1 - s1).add(b.mul(s1));
            Vec3D vBC = new Vec3D();
            Vertex vertexAB = vA.mul(1-s1).add(vB.mul(s1));
            Vertex vertexAC = vA.mul(1-s1).add(vB.mul(s1));

            for (int x = (int) vAB.getX(); x < vBC.getX(); x++) {
                double s3 = (x - x1) / (x2 - x1);
                double z = vAB.getZ() * (1 - s3) + vBC.getZ() * s1;
                Vertex vertexABC = vAB.mul(1-s3).add(vAC.mul(s1));
                zTest.test(x, y, z, shader.apply(vertexABC));

            }


        }

    }

    private Vec3D project2D(Vec3D v) {
        double x = (v.getX() + 1) * zTest.getWidth() / 2;
        double y = (-v.getY() + 1) * zTest.getHeight() / 2;
        return new Vec3D(x, y, v.getZ());
    }

}
