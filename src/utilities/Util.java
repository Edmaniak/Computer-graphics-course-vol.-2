package utilities;

import app.App;
import model.Vertex;
import transforms.Vec3D;

public class Util {

    public static double round(double n) {
        return Math.round(n * 100) / 100;
    }

    public static double lerpDouble(double val1, double val2, double par) {
        return val1 * (1.0 - par) + val2 * par;
    }

    public static int lerpInt(double val1, double val2, double par) {
        return (int) lerpDouble(val1, val2, par);
    }

    public static Vec3D crossProduct(Vec3D v1, Vec3D v2) {
        double x = (v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY());
        double y = (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ());
        double z = (v1.getX() * v2.getY()) - (v1.getY() * v2.getX());
        return new Vec3D(x, y, z);
    }

    public static double dotProduct(Vec3D v1, Vec3D v2) {
        return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY() + (v1.getZ() * v2.getZ()));
    }

    public static Vertex unProject(Vertex v) {
        double x = ((2 * v.getX()) / App.WIDTH) - 1;
        double y =((2 * v.getY()) / -App.HEIGHT) + 1;

        return new Vertex(x, y, v.getZ(), v.getColor());
    }

    public static Vec3D mul(Vec3D v, double val) {
        return new Vec3D(v.getX() * val, v.getY() * val, v.getZ() * val);
    }

    public static Vec3D sub(Vec3D v1, Vec3D v2) {
        return new Vec3D(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }

    public static Vertex project2D(Vertex v) {
        double x = (v.getX() + 1) * App.WIDTH / 2;
        double y = (-v.getY() + 1) * App.HEIGHT / 2;
        return new Vertex(x, y, v.getZ(), v.getColor());
    }

    public static Vec3D project2D(Vec3D v) {
        double x = (v.getX() + 1) * App.WIDTH / 2;
        double y = (-v.getY() + 1) * App.HEIGHT / 2;
        return new Vec3D(x, y, v.getZ());
    }

    public static Vec3D vectorize(Vertex v1, Vertex v2) {
        return new Vec3D(v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ());
    }


}
